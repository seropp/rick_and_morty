package com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case

import com.example.rickandmorty.domain.repositories.characters_repositories.GetCharacterFiltersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListCharactersSpeciesUseCase(
    private val getCharacterFiltersRepository: GetCharacterFiltersRepository
) {

    fun execute(): Flow<List<String>> =
        getCharacterFiltersRepository.getListCharactersSpecies().map { it.distinct() }
}