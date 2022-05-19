package com.example.rickandmorty.presentation.screens.characters.characters_filter_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.repositories.characters_repositories.GetCharacterFiltersRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersSpeciesUseCase
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersTypesUseCase

class CharacterFiltersViewModelProvider(
    private val getListCharactersTypesUseCase: GetListCharactersTypesUseCase,
    private val getListCharactersSpeciesUseCase: GetListCharactersSpeciesUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterFiltersViewModel(
            getListCharactersSpeciesUseCase = getListCharactersSpeciesUseCase,
            getListCharactersTypesUseCase = getListCharactersTypesUseCase
        ) as T
    }
}