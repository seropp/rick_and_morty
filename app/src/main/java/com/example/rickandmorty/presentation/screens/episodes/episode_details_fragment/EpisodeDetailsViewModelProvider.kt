package com.example.rickandmorty.presentation.screens.episodes.episode_details_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersByIdsUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases.GetEpisodeByIdUseCase


@ExperimentalPagingApi
class EpisodeDetailsViewModelProvider(
    private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    private val getAllCharactersByIdsUseCase: GetAllCharactersByIdsUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeDetailsViewModel(
            getEpisodeByIdUseCase = getEpisodeByIdUseCase,
            getAllCharactersByIdsUseCase = getAllCharactersByIdsUseCase
        ) as T
    }
}