package com.example.rickandmorty.domain.repositories.characters_repositories

import kotlinx.coroutines.flow.Flow

interface GetCharacterFiltersRepository {
    fun getListCharactersSpecies(): Flow<List<String>>

    fun getListCharactersTypes(): Flow<List<String>>
}