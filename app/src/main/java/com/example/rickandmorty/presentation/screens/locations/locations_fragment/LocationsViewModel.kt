package com.example.rickandmorty.presentation.screens.locations.locations_fragment

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.rickandmorty.domain.use_cases.locations.locations_usecases.GetAllLocationsUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetLocationPresentationModel
import com.example.rickandmorty.presentation.models.location.LocationPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@ExperimentalPagingApi
class LocationsViewModel(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {

    private var _locationsFlow: Flow<PagingData<LocationPresentation>> =
        getAllLocationsUseCase.execute().map { pagingData ->
            pagingData.map { it ->
                GetLocationPresentationModel().transform(it)
            }
        }.cachedIn(viewModelScope)

    val locationsFlow: Flow<PagingData<LocationPresentation>> = _locationsFlow

}