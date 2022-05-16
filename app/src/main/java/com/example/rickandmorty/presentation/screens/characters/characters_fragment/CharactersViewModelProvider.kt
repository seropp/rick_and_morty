package com.example.rickandmorty.presentation.screens.characters.characters_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.characters_repositories.CharactersRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersUseCase

@ExperimentalPagingApi
class CharactersViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofitInstance by lazy {
        RetrofitInstance.charactersApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val charactersRepository by lazy {
        CharactersRepositoryImpl(db = db, charactersApi = retrofitInstance)
    }

    private val getAllCharactersUseCase by lazy {
        GetAllCharactersUseCase(charactersRepository = charactersRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            getAllCharactersUseCase = getAllCharactersUseCase
        ) as T
    }
}