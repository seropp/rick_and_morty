package com.example.rickandmorty.domain.repositories.characters_repositories
import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.character.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getAllCharacters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): Flow<PagingData<CharacterModel>>

    suspend fun getAllCharactersByIds(ids: List<Int>): List<CharacterModel>

}