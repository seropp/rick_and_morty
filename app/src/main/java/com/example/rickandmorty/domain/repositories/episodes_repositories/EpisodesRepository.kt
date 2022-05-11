package com.example.rickandmorty.domain.repositories.episodes_repositories

import com.example.rickandmorty.domain.models.episode.EpisodeModel

interface EpisodesRepository {

    fun getAllEpisodes(): List<EpisodeModel>

    fun getAllEpisodesByFilters(
        name: String?,
        episode: String?
    ): List<EpisodeModel>

    fun getAllEpisodesByIds(ids: List<Int>): List<EpisodeModel>
}