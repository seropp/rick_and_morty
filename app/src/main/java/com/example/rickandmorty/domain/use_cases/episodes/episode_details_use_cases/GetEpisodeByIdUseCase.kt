package com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases

import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodeDetailsRepository

class GetEpisodeByIdUseCase(
    private val episodeDetailsRepository: EpisodeDetailsRepository
) {

    fun execute(
        id: Int
    ): EpisodeModel {
        return episodeDetailsRepository.getEpisodeById(id = id)
    }
}