package com.example.rickandmorty.data.repositories.characters_repositories

import androidx.paging.*
import com.example.rickandmorty.data.mapper.entity_to_domain_model.CharacterEntityToDomainModel
import com.example.rickandmorty.data.paging.characters_paging.CharactersRemoteMediator
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val db: RickAndMortyDatabase
) : CharactersRepository {

    @ExperimentalPagingApi
    override fun getAllCharacters(): Flow<PagingData<CharacterModel>> {

        val a: Flow<PagingData<CharacterEntity>> = Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            remoteMediator = CharactersRemoteMediator(charactersApi, db)
        ) {
            db.getCharacterDao().getAllCharacters()
        }.flow

        return a.map { pagingData ->
            pagingData.map { it ->
                CharacterEntityToDomainModel().transform(it)
            }
        }
    }

    override fun getAllCharactersByFilters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): List<CharacterModel> {
        TODO("Not yet implemented")
    }

    override fun getAllCharactersByIds(ids: List<Int>): List<CharacterModel> {
        TODO("Not yet implemented")
    }


}