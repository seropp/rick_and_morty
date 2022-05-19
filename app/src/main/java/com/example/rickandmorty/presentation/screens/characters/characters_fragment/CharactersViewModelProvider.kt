package com.example.rickandmorty.presentation.screens.characters.characters_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersUseCase

@ExperimentalPagingApi
class CharactersViewModelProvider(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            getAllCharactersUseCase = getAllCharactersUseCase
        ) as T
    }
}