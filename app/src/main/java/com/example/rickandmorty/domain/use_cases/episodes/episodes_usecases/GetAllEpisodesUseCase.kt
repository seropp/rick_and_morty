package com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases

import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository

class GetAllEpisodesUseCase(
    private val episodesRepository: EpisodesRepository
) {

    fun execute(): List<EpisodeModel> {
        return episodesRepository.getAllEpisodes()
    }
}