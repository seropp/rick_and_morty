package com.example.rickandmorty.domain.use_cases.locations.locations_usecases

import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository

class GetAllLocationsUseCase(
    private val locationsRepository: LocationsRepository
) {

    fun execute(): List<LocationModel> {
        return locationsRepository.getAllLocations()
    }
}