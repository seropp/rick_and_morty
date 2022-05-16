package com.example.rickandmorty.presentation.screens.locations.location_details_fragment

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases.GetLocationByIdUseCase

class LocationDetailsViewModel(
    private val getLocationByIdUseCase: GetLocationByIdUseCase
): ViewModel() {

}