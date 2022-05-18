package com.example.rickandmorty.presentation.screens.locations.locations_fragment

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.rickandmorty.domain.use_cases.locations.locations_usecases.GetAllLocationsUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetEpisodePresentationModel
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetLocationPresentationModel
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import com.example.rickandmorty.presentation.models.location.LocationPresentation
import kotlinx.coroutines.flow.*


@ExperimentalPagingApi
class LocationsViewModel(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {


    private val _filteredTrigger = MutableLiveData<MutableMap<String, String?>>(
        mutableMapOf(
            "name" to null,
            "type" to null,
            "dimension" to null,
        )
    )

    val filteredTrigger: MutableLiveData<MutableMap<String, String?>> = _filteredTrigger

    private var _locationsFlow = MutableSharedFlow<PagingData<LocationPresentation>>()
    val locationsFlow = _locationsFlow

    init {
        getLocationsByParams(null, null, null)
    }

    fun getLocationsByParams(
        name: String?,
        type: String?,
        dimension: String?
    ) {
        getAllLocationsUseCase.execute(
            name = name,
            type = type,
            dimension = dimension
        ).onEach {
            _locationsFlow.emit(
                it.map { obj -> GetLocationPresentationModel().transform(obj) }
            )
        }.launchIn(viewModelScope)
    }
}