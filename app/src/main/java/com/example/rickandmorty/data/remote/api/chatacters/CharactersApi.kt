package com.example.rickandmorty.data.remote.api.chatacters

import com.example.rickandmorty.data.models.characters.Characters
import com.example.rickandmorty.data.models.PagedResponse
import retrofit2.Response
import retrofit2.http.*

interface CharactersApi {

    /**
     * Get character page.
     * @param page - Characters page.
     * @param name - Character's name.
     * @param status - Character's status. (For ex.: "Dead")
     * @param species - Character's species. (For ex.: "Poopybutthole")
     * @param type - Character's type. (For ex.: "Human")
     * @param gender - Character's gender. (For ex.: "Female")
     *
     * @return - Response from the server.
     */
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): PagedResponse<Characters>

    /**
     * Get characters by id.
     * @param ids - String with ids. (For ex.: "1,2,3")
     * @return - Response from the server.
     */
    @GET("character/{ids}")
    suspend fun getCharactersByIds(
        @Path("ids") ids: String
    ): Response<List<Characters>>
}