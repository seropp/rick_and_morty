package com.example.rickandmorty.domain.use_cases.characters.characters_usecases

import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository

class GetAllCharactersByIdsUseCase(
    private val charactersRepository: CharactersRepository
) {

    suspend fun execute(
        ids: List<Int>
    ): List<CharacterModel> {
        return charactersRepository.getAllCharactersByIds(
            ids = ids
        )
    }
}