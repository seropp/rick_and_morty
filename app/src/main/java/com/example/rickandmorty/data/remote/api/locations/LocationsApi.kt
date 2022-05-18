package com.example.rickandmorty.data.remote.api.locations

import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.location.Location
import retrofit2.Response
import retrofit2.http.*

interface LocationsApi {

    /**
     * Get locations by filters.
     * @param page - Locations page.
     * @param type - Types of the location.
     * @param dimension - Dimensions of the location.
     * @return  -  Response from the server.
     */
    @GET("location/")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?
    ): PagedResponse<Location>

    /**
     * Get locations by id.
     * @param ids - String with ids.
     * @return - Response from the server.
     */
    @GET("location/{ids}")
    suspend fun getLocationsByIds(
        @Path("ids") ids: String
    ): Response<List<Location>>


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
    ): Response<List<Location>>
}