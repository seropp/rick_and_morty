package com.example.rickandmorty.domain.use_cases.locations.locations_usecases

import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository

class GetAllLocationsByFiltersUseCase(
    private val locationsRepository: LocationsRepository
) {

    suspend fun execute(
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationModel> {
        return locationsRepository.getAllLocationsByFilters(
            name = name,
            type = type,
            dimension = dimension
        )
    }
}