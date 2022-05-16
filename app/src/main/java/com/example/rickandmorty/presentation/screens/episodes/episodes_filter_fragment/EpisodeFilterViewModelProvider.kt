package com.example.rickandmorty.presentation.screens.episodes.episodes_filter_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.repositories.episodes_repositories.GetEpisodeFiltersRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.episodes.episode_filters_use_cases.GetListEpisodesUseCase

class EpisodeFilterViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val getEpisodeFiltersRepository by lazy {
        GetEpisodeFiltersRepositoryImpl(db = db)
    }

    private val getListEpisodesUseCase by lazy {
        GetListEpisodesUseCase(getEpisodeFiltersRepository = getEpisodeFiltersRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeFiltersViewModel(
            getListEpisodesUseCase = getListEpisodesUseCase
        ) as T
    }
}