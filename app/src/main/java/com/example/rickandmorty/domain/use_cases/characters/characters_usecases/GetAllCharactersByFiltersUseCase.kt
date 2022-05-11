package com.example.rickandmorty.domain.use_cases.characters.characters_usecases

import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository

class GetAllCharactersByFiltersUseCase(
    private val charactersRepository: CharactersRepository
) {

    fun execute(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): List<CharacterModel> {
        return charactersRepository.getAllCharactersByFilters(
            name = name,
            status = status,
            gender = gender,
            type = type,
            species = species
        )
    }
}