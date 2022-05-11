package com.example.rickandmorty.data.paging.characters_paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.mapper.dto_to_entity.CharacterDtoToEntity
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.storage.room.dao.PageKeyDao
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.data.storage.room.entities.PageKeyEntity
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity


@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val charactersApi: CharactersApi,
    private val db: RickAndMortyDatabase
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = db.getCharacterDao()
    private val pageKeyDao = db.getPageKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    lastItem.id
                }
            }
            Log.e("TAGGGGGGGGGGGGGGGGGG", "2")
            val response = charactersApi.getCharacterPage(loadKey ?: 1)
            val resBody = response.body()

            val pageInfo = resBody?.pageInfo
            val characters = resBody?.results
            val convertType: List<CharacterEntity>? = characters?.map {
                CharacterDtoToEntity().transform(it)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAllCharacters()
                    pageKeyDao.clearAll()
                }
                characters?.forEach {
                    it.page = loadKey
                    pageKeyDao.insertOrReplace(PageKeyEntity(it.id, pageInfo?.next))
                }
                convertType?.let { characterDao.insertAllCharacters(it) }

            }

            MediatorResult.Success(
                endOfPaginationReached = resBody?.pageInfo?.next == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}