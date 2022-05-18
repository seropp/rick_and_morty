package com.example.rickandmorty.domain.use_cases.locations.locations_usecases

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository
import kotlinx.coroutines.flow.Flow

class GetAllLocationsUseCase(
    private val locationsRepository: LocationsRepository
) {

    fun execute(
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<PagingData<LocationModel>> {
        return locationsRepository.getAllLocations(
            name = name,
            type = type,
            dimension = dimension
        )
    }
}