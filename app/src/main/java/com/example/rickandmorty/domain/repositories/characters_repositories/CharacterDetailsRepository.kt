package com.example.rickandmorty.domain.repositories.characters_repositories

import com.example.rickandmorty.domain.models.character.CharacterModel

interface CharacterDetailsRepository {

    fun getCharacterById(id: Int): CharacterModel
}