package com.example.rickandmorty.domain.repositories.characters_repositories

import com.example.rickandmorty.domain.models.character.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsRepository {

    suspend fun getCharacterById(id: Int): CharacterModel
}