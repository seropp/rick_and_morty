package com.example.rickandmorty.presentation.screens.locations.locations_filter_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.domain.use_cases.locations.location_filters_use_cases.GetListLocationsDimensionsUseCase
import com.example.rickandmorty.domain.use_cases.locations.location_filters_use_cases.GetListLocationsTypesUseCase

class LocationFiltersViewModelProvider(
    private val getListLocationsDimensionsUseCase: GetListLocationsDimensionsUseCase,
    private val getListLocationsTypesUseCase: GetListLocationsTypesUseCase
) : ViewModelProvider.Factory {




    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationFiltersViewModel(
            getListLocationsDimensionsUseCase = getListLocationsDimensionsUseCase,
            getListLocationsTypesUseCase = getListLocationsTypesUseCase,
        ) as T
    }
}