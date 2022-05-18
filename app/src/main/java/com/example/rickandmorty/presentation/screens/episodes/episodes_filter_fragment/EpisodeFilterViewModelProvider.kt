package com.example.rickandmorty.presentation.screens.episodes.episodes_filter_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.repositories.episodes_repositories.GetEpisodeFiltersRepositoryImpl
import com.example.rickandmorty.data.repositories.settings_repositories.EpisodeSettingsRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.data.storage.sharedPref.EpisodeSettingsPref
import com.example.rickandmorty.domain.use_cases.episodes.episode_filters_use_cases.GetListEpisodesUseCase
import com.example.rickandmorty.domain.use_cases.settings.EpisodesSettingsUseCases

class EpisodeFilterViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val getEpisodeFiltersRepository by lazy {
        GetEpisodeFiltersRepositoryImpl(db = db)
    }
    private val episodeSettingsPref by lazy {
        EpisodeSettingsPref(context = context)
    }

    private val episodesSettingsRepository by lazy {
        EpisodeSettingsRepositoryImpl(episodeSettingsPref = episodeSettingsPref)
    }

    private val getListEpisodesUseCase by lazy {
        GetListEpisodesUseCase(getEpisodeFiltersRepository = getEpisodeFiltersRepository)
    }

    private val episodesSettingsUseCase by lazy {
        EpisodesSettingsUseCases(episodesSettingsRepository = episodesSettingsRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeFiltersViewModel(
            getListEpisodesUseCase = getListEpisodesUseCase,
            episodesSettingsUseCase = episodesSettingsUseCase
        ) as T
    }
}