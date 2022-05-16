package com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetAllEpisodesUseCase(
    private val episodesRepository: EpisodesRepository
) {

    fun execute(): Flow<PagingData<EpisodeModel>> {
        return episodesRepository.getAllEpisodes()
    }
}