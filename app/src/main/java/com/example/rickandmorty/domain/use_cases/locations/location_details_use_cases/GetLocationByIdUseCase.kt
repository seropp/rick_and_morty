package com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases

import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationDetailsRepository

class GetLocationByIdUseCase(
    private val locationDetailsRepository: LocationDetailsRepository
) {

    fun execute(
        id: Int
    ): LocationModel {
        return locationDetailsRepository.getLocationById(
            id = id
        )
    }
}