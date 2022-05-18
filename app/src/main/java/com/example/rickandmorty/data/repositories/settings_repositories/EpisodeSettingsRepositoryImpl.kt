package com.example.rickandmorty.data.repositories.settings_repositories

import com.example.rickandmorty.data.storage.sharedPref.EpisodeSettingsPref
import com.example.rickandmorty.domain.repositories.settings_repositories.EpisodeSettingsRepository

class EpisodeSettingsRepositoryImpl(
    private val episodeSettingsPref: EpisodeSettingsPref
) : EpisodeSettingsRepository {

    override suspend fun saveEpisodes(episodes: Map<String, List<String>>) {
        episodeSettingsPref.save(episodes = episodes)
    }
    override suspend fun getEpisodes(): Map<String, List<String>> {
        return episodeSettingsPref.get()
    }
}