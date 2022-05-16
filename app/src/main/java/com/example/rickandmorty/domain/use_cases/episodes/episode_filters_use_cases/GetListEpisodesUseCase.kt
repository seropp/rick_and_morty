package com.example.rickandmorty.domain.use_cases.episodes.episode_filters_use_cases

import com.example.rickandmorty.domain.repositories.episodes_repositories.GetEpisodeFiltersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListEpisodesUseCase(
    private val getEpisodeFiltersRepository: GetEpisodeFiltersRepository
) {

    fun execute(): Flow<List<String>> =
        getEpisodeFiltersRepository.getListEpisodes().map { it.distinct() }
}