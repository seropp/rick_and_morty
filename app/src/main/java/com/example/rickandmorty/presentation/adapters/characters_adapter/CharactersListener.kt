package com.example.rickandmorty.presentation.adapters.characters_adapter

import com.example.rickandmorty.presentation.models.character.CharacterPresentation

interface CharactersListener {
    fun onItemClick(id: Int)
}