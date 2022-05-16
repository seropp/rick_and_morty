package com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases

import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodeDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeByIdUseCase(
    private val episodeDetailsRepository: EpisodeDetailsRepository
) {

    suspend fun execute(id: Int): EpisodeModel =
        episodeDetailsRepository.getEpisodeById(id = id)
}