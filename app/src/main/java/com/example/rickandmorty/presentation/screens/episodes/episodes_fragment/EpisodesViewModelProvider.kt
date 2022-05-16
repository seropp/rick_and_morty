package com.example.rickandmorty.presentation.screens.episodes.episodes_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodesRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesUseCase

@ExperimentalPagingApi
class EpisodesViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofitInstance by lazy {
        RetrofitInstance.episodesApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val episodesRepository by lazy {
        EpisodesRepositoryImpl(db = db, episodesApi = retrofitInstance)
    }

    private val getAllEpisodesUseCase by lazy {
        GetAllEpisodesUseCase(episodesRepository = episodesRepository)
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodesViewModel(
            getAllEpisodesUseCase = getAllEpisodesUseCase
        ) as T
    }
}