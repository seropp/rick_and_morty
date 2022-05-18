package com.example.rickandmorty.presentation.screens.episodes.episodes_fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetEpisodePresentationModel
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class EpisodesViewModel(
    private val getAllEpisodesUseCase: GetAllEpisodesUseCase
) : ViewModel() {


    private val _filteredTrigger = MutableStateFlow<MutableMap<String, String?>>(
        mutableMapOf(
            "name" to null,
            "episode" to null
        )
    )

    val filteredTrigger: MutableStateFlow<MutableMap<String, String?>> = _filteredTrigger

    private var _episodesFlow = MutableSharedFlow<PagingData<EpisodePresentation>>()
    val episodesFlow = _episodesFlow

    fun getEpisodeByParams(
        name: String?,
        episode: String?
    ) {
        getAllEpisodesUseCase.execute(
            name = name,
            episode = episode
        ).onEach {
            _episodesFlow.emit(
                it.map { obj -> GetEpisodePresentationModel().transform(obj) }
            )
        }.launchIn(viewModelScope)

    }
}