package com.example.rickandmorty.domain.use_cases.characters.character_details_use_cases

import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharacterDetailsRepository

class GetCharacterByIdUseCase(
    private val characterDetailsRepository: CharacterDetailsRepository
) {

    suspend fun execute(id: Int): CharacterModel =
        characterDetailsRepository.getCharacterById(id = id)
}