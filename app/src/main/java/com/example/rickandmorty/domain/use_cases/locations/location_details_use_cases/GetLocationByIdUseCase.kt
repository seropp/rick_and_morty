package com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases

import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetLocationByIdUseCase(
    private val locationDetailsRepository: LocationDetailsRepository
) {

    suspend fun execute(id: Int): LocationModel =
        locationDetailsRepository.getLocationById(id = id)
}