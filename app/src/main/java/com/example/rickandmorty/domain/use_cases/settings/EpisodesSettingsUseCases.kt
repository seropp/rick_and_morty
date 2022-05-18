package com.example.rickandmorty.domain.use_cases.settings

import com.example.rickandmorty.domain.repositories.settings_repositories.EpisodeSettingsRepository

class EpisodesSettingsUseCases(private val episodesSettingsRepository: EpisodeSettingsRepository) {

    suspend fun saveEpisodes(episodes: Map<String, List<String>>) {
        episodesSettingsRepository.saveEpisodes(episodes = episodes)
    }

    suspend fun getEpisodes(): Map<String, List<String>> {
        return episodesSettingsRepository.getEpisodes()
    }
}