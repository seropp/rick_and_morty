package com.example.rickandmorty.data.remote.api.locations

import com.example.rickandmorty.data.remote.dto.locationDto.LocationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsApi {

    @GET("location/?page={page}")
    suspend fun getLocationPage(
        @Path("page") page: Int
    ): Response<List<LocationDto>>

    /**
     * Get locations by id.
     * @param ids - String with ids.
     * @return - Response from the server.
     */
    @GET("location/{ids}")
    suspend fun getLocationsByIds(
        @Path("ids") ids: String
    ): Response<List<LocationDto>>


    /**
     * Get locations by filters.
     * @param type - Types of the location.
     * @param dimension - Dimensions of the location.
     * @return  -  Response from the server.
     */
    @GET("location/")
    suspend fun getLocationsWithFilters(
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?
    ): Response<List<LocationDto>>
}