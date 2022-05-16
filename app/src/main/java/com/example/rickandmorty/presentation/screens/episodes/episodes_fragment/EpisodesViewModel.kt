package com.example.rickandmorty.presentation.screens.episodes.episodes_fragment

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetEpisodePresentationModel
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@ExperimentalPagingApi
class EpisodesViewModel(
    private val getAllEpisodesUseCase: GetAllEpisodesUseCase
) : ViewModel() {

    private var _episodesFlow: Flow<PagingData<EpisodePresentation>> =
        getAllEpisodesUseCase.execute().map { pagingData ->
            pagingData.map { it ->
                GetEpisodePresentationModel().transform(it)
            }
        }.cachedIn(viewModelScope)

    val episodesFlow: Flow<PagingData<EpisodePresentation>> = _episodesFlow

}