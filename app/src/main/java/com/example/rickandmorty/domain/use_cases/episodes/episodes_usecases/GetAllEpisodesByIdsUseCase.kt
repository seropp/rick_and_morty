package com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases

import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository

class GetAllEpisodesByIdsUseCase(
    private val episodesRepository: EpisodesRepository
) {

    suspend fun execute(
        ids: List<Int>
    ): List<EpisodeModel> {
        return episodesRepository.getAllEpisodesByIds(ids = ids)
    }
}