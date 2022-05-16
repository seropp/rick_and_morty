package com.example.rickandmorty.data.remote.api.episodes

import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.episodes.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApi {

    /**
     * Get episode page.
     * @param page - Episode page.
     * @return - Response from the server.
     */
    @GET("episode/")
    suspend fun getEpisodePage(
        @Query("page") page: Int
    ): Response<PagedResponse<Episode>>

    /**
     * Get episodes by ids.
     * @param ids - String with ids. (For ex.: "1,2,3")
     * @return - Response from the server.
     */
    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path("ids") ids: String
    ): Response<List<Episode>>

    /**
     * Get episodes by filters.
     * @param episode - Episode.
     * @return  -  Response from the server.
     */
    @GET("episode/")
    suspend fun getEpisodesWithFilters(
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): Response<List<Episode>>
}