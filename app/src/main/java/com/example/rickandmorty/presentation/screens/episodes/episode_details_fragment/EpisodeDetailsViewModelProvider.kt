package com.example.rickandmorty.presentation.screens.episodes.episode_details_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.characters_repositories.CharacterDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.characters_repositories.CharactersRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodeDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodesRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodeDetailsRepository
import com.example.rickandmorty.domain.use_cases.characters.character_details_use_cases.GetCharacterByIdUseCase
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersByIdsUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases.GetEpisodeByIdUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesByIdsUseCase
import com.example.rickandmorty.presentation.screens.characters.character_details_fragment.CharacterDetailsViewModel


@ExperimentalPagingApi
class EpisodeDetailsViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofit by lazy {
        RetrofitInstance
    }

    private val characterDetailsApi by lazy {
        retrofit.characterDetailsApi
    }

    private val episodeDetailsApi by lazy {
        retrofit.episodeDetailsApi
    }

    private val charactersApi by lazy {
        retrofit.charactersApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val episodeDetailsRepository by lazy {
        EpisodeDetailsRepositoryImpl(episodeDetailsApi = episodeDetailsApi, db = db)
    }

    private val charactersRepository by lazy {
        CharactersRepositoryImpl(
            characterDetailsApi = characterDetailsApi,
            charactersApi = charactersApi,
            db = db
        )
    }

    private val getEpisodeByIdUseCase by lazy {
        GetEpisodeByIdUseCase(episodeDetailsRepository = episodeDetailsRepository)
    }

    private val getAllCharactersByIdsUseCase by lazy {
        GetAllCharactersByIdsUseCase(charactersRepository = charactersRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeDetailsViewModel(
            getEpisodeByIdUseCase = getEpisodeByIdUseCase,
            getAllCharactersByIdsUseCase = getAllCharactersByIdsUseCase
        ) as T
    }
}