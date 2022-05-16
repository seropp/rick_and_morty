package com.example.rickandmorty.data.repositories.locations_repositories

import android.util.Log
import com.example.rickandmorty.data.mapper.entity_to_domain_model.LocationEntityToDomainModel
import com.example.rickandmorty.data.models.location.Location
import com.example.rickandmorty.data.remote.api.locations.LocationDetailsApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.models.location.LocationModel
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class LocationDetailsRepositoryImpl(
    private val locationDetailsApi: LocationDetailsApi,
    private val db: RickAndMortyDatabase
) : LocationDetailsRepository {

    override suspend fun getLocationById(id: Int): LocationModel = withContext(Dispatchers.IO) {
        try {
            val locationFromApi: Response<Location> =
                locationDetailsApi.getLocationById(id = id)
            if (locationFromApi.isSuccessful) {
                locationFromApi.body()
                    ?.let { db.getLocationDao().insertLocation(location = it) }
            }

        } catch (e: HttpException) {
            Log.e("Log", "${e.code()}")
        } catch (e: IOException) {
            Log.e("Log", "${e.message}")
        }

        return@withContext LocationEntityToDomainModel().transform(
            db.getLocationDao().getLocationById(id = id)
        )
    }
}