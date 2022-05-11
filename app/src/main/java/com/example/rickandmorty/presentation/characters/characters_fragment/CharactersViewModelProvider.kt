package com.example.rickandmorty.presentation.characters.characters_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.repositories.characters_repositories.CharactersRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalPagingApi
class CharactersViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val charactersApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApi::class.java)

    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val charactersRepository by lazy {
        CharactersRepositoryImpl(db = db, charactersApi = charactersApi)
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