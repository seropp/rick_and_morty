package com.example.rickandmorty.presentation.screens.episodes.episodes_filter_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.domain.use_cases.episodes.episode_filters_use_cases.GetListEpisodesUseCase


class EpisodeFilterViewModelProvider(
    private val getListEpisodesUseCase: GetListEpisodesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeFiltersViewModel(
            getListEpisodesUseCase = getListEpisodesUseCase,
        ) as T
    }
}