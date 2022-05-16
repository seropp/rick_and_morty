package com.example.rickandmorty.domain.use_cases.locations.locations_usecases

import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository

class GetAllLocationsByIdsUseCase(
    private val locationsRepository: LocationsRepository
) {

    suspend fun execute(
        ids: List<Int>
    ): List<LocationModel> {
        return locationsRepository.getAllLocationsByIds(
            ids = ids
        )
    }
}