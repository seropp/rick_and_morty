package com.example.rickandmorty.data.remote.api.episodes

import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.episodes.Episode
import retrofit2.Response
import retrofit2.http.*

interface EpisodesApi {

    /**
     * Get episode page.
     * @param page - Episodes page.
     * @param name - name of episodes.
     * @param episode - Episode.
     * @return - Response from the server.
     */
    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): PagedResponse<Episode>

    /**
     * Get episodes by ids.
     * @param ids - String with ids. (For ex.: "1,2,3")
     * @return - Response from the server.
     */
    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path("ids") ids: String
    ): Response<List<Episode>>
}