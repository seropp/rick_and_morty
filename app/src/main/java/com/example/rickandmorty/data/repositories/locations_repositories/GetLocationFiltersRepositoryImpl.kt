package com.example.rickandmorty.data.repositories.locations_repositories

import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.repositories.locations_repositories.GetLocationFiltersRepository
import kotlinx.coroutines.flow.Flow

class GetLocationFiltersRepositoryImpl(
    private val db: RickAndMortyDatabase
) : GetLocationFiltersRepository {

    override fun getListLocationsTypes(): Flow<List<String>> =
        db.getLocationDao().getTypes()

    override fun getListLocationsDimensions(): Flow<List<String>> =
        db.getLocationDao().getDimensions()
}