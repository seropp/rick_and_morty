package com.example.rickandmorty.data.repositories.locations_repositories

import androidx.paging.*
import com.example.rickandmorty.data.mapper.entity_to_domain_model.EpisodeEntityToDomainModel
import com.example.rickandmorty.data.mapper.entity_to_domain_model.LocationEntityToDomainModel
import com.example.rickandmorty.data.paging.epispdes_paging.EpisodesRemoteMediator
import com.example.rickandmorty.data.paging.locations_paging.LocationsRemoteMediator
import com.example.rickandmorty.data.remote.api.locations.LocationsApi
import com.example.rickandmorty.data.storage.room.dao.LocationDao
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationsRepositoryImpl(
    private val locationsApi: LocationsApi,
    private val db: RickAndMortyDatabase
): LocationsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllLocations(): Flow<PagingData<LocationModel>> {
        val pagingSourceFactory = { db.getLocationDao().getAllLocations() }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = LocationsRemoteMediator(
                locationsApi = locationsApi,
                db = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it ->
                LocationEntityToDomainModel().transform(it)
            }
        }
    }

    override suspend fun getAllLocationsByFilters(
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLocationsByIds(ids: List<Int>): List<LocationModel> {
        TODO("Not yet implemented")
    }


}