package com.example.rickandmorty.data.remote.api

import com.example.rickandmorty.data.remote.api.chatacters.CharacterDetailsApi
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.remote.api.episodes.EpisodeDetailsApi
import com.example.rickandmorty.data.remote.api.episodes.EpisodesApi
import com.example.rickandmorty.data.remote.api.locations.LocationDetailsApi
import com.example.rickandmorty.data.remote.api.locations.LocationsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val CHARACTER_API: CharactersApi by lazy {
            retrofit.create(CharactersApi::class.java)
        }

        val CHARACTER_DETAIL_API: CharacterDetailsApi by lazy {
            retrofit.create(CharacterDetailsApi::class.java)
        }

        val EPISODE_API: EpisodesApi by lazy {
            retrofit.create(EpisodesApi::class.java)
        }

        val EPISODE_DETAIL_API: EpisodeDetailsApi by lazy {
            retrofit.create(EpisodeDetailsApi::class.java)
        }

        val LOCATION_API: LocationsApi by lazy {
            retrofit.create(LocationsApi::class.java)
        }

        val LOCATION_DETAILS_API: LocationDetailsApi by lazy {
            retrofit.create(LocationDetailsApi::class.java)
        }


    }
}