package com.example.rickandmorty.presentation.screens.locations.location_details_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.characters_repositories.CharacterDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.characters_repositories.CharactersRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodeDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodesRepositoryImpl
import com.example.rickandmorty.data.repositories.locations_repositories.LocationDetailsRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.characters.character_details_use_cases.GetCharacterByIdUseCase
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersByIdsUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases.GetEpisodeByIdUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesByIdsUseCase
import com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases.GetLocationByIdUseCase
import com.example.rickandmorty.presentation.screens.characters.character_details_fragment.CharacterDetailsViewModel


class LocationDetailsViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofit by lazy {
        RetrofitInstance
    }

    private val characterDetailsApi by lazy {
        retrofit.characterDetailsApi
    }

    private val locationDetailsApi by lazy {
        retrofit.locationDetailsApi
    }

    private val charactersApi by lazy {
        retrofit.charactersApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val locationDetailsRepository by lazy {
        LocationDetailsRepositoryImpl(locationDetailsApi = locationDetailsApi, db = db)
    }

    private val charactersRepository by lazy {
        CharactersRepositoryImpl(
            charactersApi = charactersApi,
            characterDetailsApi = characterDetailsApi,
            db = db
        )
    }

    private val getLocationByIdUseCase by lazy {
        GetLocationByIdUseCase(locationDetailsRepository = locationDetailsRepository)
    }

    private val getAllCharactersByIdsUseCase by lazy {
        GetAllCharactersByIdsUseCase(charactersRepository = charactersRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationDetailsViewModel(
            getLocationByIdUseCase = getLocationByIdUseCase,
            getAllCharactersByIdsUseCase = getAllCharactersByIdsUseCase
        ) as T
    }
}