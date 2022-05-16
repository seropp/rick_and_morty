package com.example.rickandmorty.data.remote.api.locations

import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.location.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsApi {

    @GET("location/")
    suspend fun getLocationPage(
        @Query("page") page: Int,
    ): Response<PagedResponse<Location>>

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