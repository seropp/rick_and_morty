package com.example.rickandmorty.data.remote.api.episodes

import com.example.rickandmorty.data.remote.dto.episodeDto.EpisodeDto
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
    @GET("episode/?page={page}")
    suspend fun getEpisodePage(
        @Path("page") page: Int
    ): Response<List<EpisodeDto>>

    /**
     * Get episodes by ids.
     * @param ids - String with ids. (For ex.: "1,2,3")
     * @return - Response from the server.
     */
    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path("ids") ids: String
    ): Response<List<EpisodeDto>>

    /**
     * Get episodes by filters.
     * @param episode - Episode.
     * @return  -  Response from the server.
     */
    @GET("episode/")
    suspend fun getEpisodesWithFilters(
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): Response<List<EpisodeDto>>
}