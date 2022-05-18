package com.example.rickandmorty.domain.repositories.settings_repositories

interface EpisodeSettingsRepository {

    suspend fun saveEpisodes(episodes: Map<String, List<String>>)

    suspend fun getEpisodes(): Map<String, List<String>>
}