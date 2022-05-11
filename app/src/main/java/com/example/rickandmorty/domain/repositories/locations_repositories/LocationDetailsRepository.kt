package com.example.rickandmorty.domain.repositories.locations_repositories

import com.example.rickandmorty.domain.models.location.LocationModel

interface LocationDetailsRepository {

    fun getLocationById(id: Int): LocationModel
}