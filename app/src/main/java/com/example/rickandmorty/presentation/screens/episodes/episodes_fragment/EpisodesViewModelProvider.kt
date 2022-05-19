package com.example.rickandmorty.presentation.screens.episodes.episodes_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesUseCase

@ExperimentalPagingApi
class EpisodesViewModelProvider(
    private val getAllEpisodesUseCase: GetAllEpisodesUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodesViewModel(
            getAllEpisodesUseCase = getAllEpisodesUseCase
        ) as T
    }
}