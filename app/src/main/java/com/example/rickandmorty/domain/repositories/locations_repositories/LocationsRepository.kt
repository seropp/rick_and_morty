package com.example.rickandmorty.domain.repositories.locations_repositories

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.location.LocationModel
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    fun getAllLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): Flow<PagingData<LocationModel>>

    suspend fun getAllLocationsByIds(ids: List<Int>): List<LocationModel>
}