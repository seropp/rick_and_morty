package com.example.rickandmorty.domain.use_cases.characters.characters_usecases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {

    @ExperimentalPagingApi
    fun execute(): Flow<PagingData<CharacterModel>> {
        return charactersRepository.getAllCharacters()
    }
}