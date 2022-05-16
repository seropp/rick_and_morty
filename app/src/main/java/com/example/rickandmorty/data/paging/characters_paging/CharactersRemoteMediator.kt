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
import retrofit2.Response
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val charactersApi: CharactersApi,
    private val db: RickAndMortyDatabase
) : RemoteMediator<Int, Characters>() {

    private val charactersDao = db.getCharacterDao()
    private val keyDao = db.getCharactersKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Characters>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response: Response<PagedResponse<Characters>> =
                charactersApi.getCharacterPage(page = currentPage)

            val resBody = response.body()
            val characters = resBody?.results
            val endOfPaginationReached = characters?.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    charactersDao.deleteAllCharacters()
                    keyDao.deleteAllCharactersRemoteKeys()
                }
                val keys = characters?.map { character ->
                    CharactersPageKeys(
                        id = character.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { keyDao.insertAllCharactersKeys(remoteKeysCharacters = it) }
                characters?.let { charactersDao.insertAllCharacters(characters = it) }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
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
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                keyDao.getCharactersRemoteKeys(id = character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Characters>
    ): CharactersPageKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                keyDao.getCharactersRemoteKeys(id = character.id)
            }
    }
}