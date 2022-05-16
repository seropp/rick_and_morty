package com.example.rickandmorty.domain.repositories.episodes_repositories

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun getAllEpisodes(): Flow<PagingData<EpisodeModel>>

    fun getAllEpisodesByFilters(
        name: String?,
        episode: String?
    ): List<EpisodeModel>

    fun getAllEpisodesByIds(ids: List<Int>): List<EpisodeModel>
}