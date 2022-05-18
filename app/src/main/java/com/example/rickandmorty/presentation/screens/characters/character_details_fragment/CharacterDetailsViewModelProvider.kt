package com.example.rickandmorty.presentation.screens.characters.character_details_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.characters_repositories.CharacterDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodesRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.character_details_use_cases.GetCharacterByIdUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesByIdsUseCase


@ExperimentalPagingApi
class CharacterDetailsViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofit by lazy {
        RetrofitInstance
    }

    private val characterDetailsApi by lazy {
        retrofit.characterDetailsApi
    }

    private val episodeDetailsApi by lazy {
        retrofit.episodeDetailsApi
    }

    private val episodesApi by lazy {
        retrofit.episodesApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val characterDetailsRepository by lazy {
        CharacterDetailsRepositoryImpl(characterDetailsApi = characterDetailsApi, db = db)
    }

    private val episodesRepository by lazy {
        EpisodesRepositoryImpl(
            episodesApi = episodesApi,
            episodeDetailsApi = episodeDetailsApi,
            db = db)
    }

    private val getCharacterByIdUseCase by lazy {
        GetCharacterByIdUseCase(characterDetailsRepository = characterDetailsRepository)
    }

    private val getAllEpisodesByIdsUseCase by lazy {
        GetAllEpisodesByIdsUseCase(episodesRepository = episodesRepository)
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailsViewModel(
            getCharacterByIdUseCase = getCharacterByIdUseCase,
            getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase
        ) as T
    }
}