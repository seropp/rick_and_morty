package com.example.rickandmorty.domain.repositories.locations_repositories

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.location.LocationModel
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    fun getAllLocations(): Flow<PagingData<LocationModel>>

    fun getAllLocationsByFilters(
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationModel>

    fun getAllLocationsByIds(ids: List<Int>): List<LocationModel>
}