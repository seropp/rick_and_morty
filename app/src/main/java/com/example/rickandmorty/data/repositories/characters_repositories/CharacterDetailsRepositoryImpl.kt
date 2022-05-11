package com.example.rickandmorty.data.repositories.characters_repositories

import com.example.rickandmorty.data.storage.room.dao.CharacterDao
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi

class CharacterDetailsRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val characterDao: CharacterDao
) {


}