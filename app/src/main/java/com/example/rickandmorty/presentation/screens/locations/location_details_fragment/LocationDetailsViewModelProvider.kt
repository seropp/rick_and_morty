package com.example.rickandmorty.presentation.screens.locations.location_details_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersByIdsUseCase
import com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases.GetLocationByIdUseCase

@ExperimentalPagingApi
class LocationDetailsViewModelProvider(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getAllCharactersByIdsUseCase: GetAllCharactersByIdsUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationDetailsViewModel(
            getLocationByIdUseCase = getLocationByIdUseCase,
            getAllCharactersByIdsUseCase = getAllCharactersByIdsUseCase
        ) as T
    }
}