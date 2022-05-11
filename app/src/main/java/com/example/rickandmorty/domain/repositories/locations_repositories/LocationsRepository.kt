package com.example.rickandmorty.domain.repositories.locations_repositories

import com.example.rickandmorty.domain.models.location.LocationModel

interface LocationsRepository {

    fun getAllLocations(): List<LocationModel>

    fun getAllLocationsByFilters(
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationModel>

    fun getAllLocationsByIds(ids: List<Int>): List<LocationModel>
}