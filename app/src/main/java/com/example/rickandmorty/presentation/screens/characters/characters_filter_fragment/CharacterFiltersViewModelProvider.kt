package com.example.rickandmorty.presentation.screens.characters.characters_filter_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.repositories.characters_repositories.GetCharacterFiltersRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersSpeciesUseCase
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersTypesUseCase

class CharacterFiltersViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val getCharacterFiltersRepository by lazy {
        GetCharacterFiltersRepositoryImpl(db = db)
    }

    private val getListTypesSpeciesUseCase by lazy {
        GetListCharactersTypesUseCase(getCharacterFiltersRepository = getCharacterFiltersRepository)
    }

    private val getListCharactersSpeciesUseCase by lazy {
        GetListCharactersSpeciesUseCase(getCharacterFiltersRepository = getCharacterFiltersRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterFiltersViewModel(
            getListCharactersSpeciesUseCase = getListCharactersSpeciesUseCase,
            getListTypesSpeciesUseCase = getListTypesSpeciesUseCase
        ) as T
    }
}