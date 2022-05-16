package com.example.rickandmorty.domain.repositories.locations_repositories

import com.example.rickandmorty.domain.models.location.LocationModel
import kotlinx.coroutines.flow.Flow

interface LocationDetailsRepository {

    suspend fun getLocationById(id: Int): LocationModel
}