package com.example.rickandmorty.data.paging.characters_paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.models.characters.Characters
import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.page_keys.CharactersPageKeys
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val charactersApi: CharactersApi,
    private val db: RickAndMortyDatabase,
    private val name: String?,
    private val status: String?,
    private val gender: String?,
    private val type: String?,
    private val species: String?
) : RemoteMediator<Int, Characters>() {

    private val charactersDao = db.getCharacterDao()
    private val keyDao = db.getCharactersKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Characters>
    ): MediatorResult {


        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {

            val response: PagedResponse<Characters> =
                charactersApi.getCharacters(
                    page = page,
                    name = name,
                    status = status,
                    gender = gender,
                    type = type,
                    species = species
                )


            val isEndOfList =
                response.info.next == null
                        || response.toString().contains("error")
                        || response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    charactersDao.deleteAllCharacters()
                    keyDao.deleteAllCharactersRemoteKeys()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    CharactersPageKeys(it.id, prevPage = prevKey, nextPage = nextKey)
                }
                keyDao.insertAllCharactersKeys(remoteKeysCharacters = keys)
                charactersDao.insertAllCharacters(characters = response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Characters>
    ): CharactersPageKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keyDao.getCharactersRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Characters>
    ): CharactersPageKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { it ->
                keyDao.getCharactersRemoteKeys(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Characters>
    ): CharactersPageKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { it ->
                keyDao.getCharactersRemoteKeys(id = it.id)
            }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, Characters>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                return nextPage ?: RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
            }
        }
    }

}