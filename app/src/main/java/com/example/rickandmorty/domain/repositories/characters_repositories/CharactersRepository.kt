package com.example.rickandmorty.domain.repositories.characters_repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.character.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    @ExperimentalPagingApi
    fun getAllCharacters(): Flow<PagingData<CharacterModel>>

    fun getAllCharactersByFilters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): List<CharacterModel>

    fun getAllCharactersByIds(ids: List<Int>): List<CharacterModel>

}