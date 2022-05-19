package com.example.rickandmorty.presentation.screens.locations.locations_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.domain.use_cases.locations.locations_usecases.GetAllLocationsUseCase

@ExperimentalPagingApi
class LocationsViewModelProvider(
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationsViewModel(
            getAllLocationsUseCase = getAllLocationsUseCase
        ) as T
    }
}