package com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases

import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository

class GetAllEpisodesByFiltersUseCase(
    private val episodesRepository: EpisodesRepository
) {

    suspend fun execute(
        name: String?,
        episode: String?
    ): List<EpisodeModel> {
        return episodesRepository.getAllEpisodesByFilters(
            name = name,
            episode = episode
        )
    }
}