package com.example.rickandmorty.domain.repositories.episodes_repositories

import androidx.paging.PagingData
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun getAllEpisodes(
        name: String?,
        episode: String?
    ): Flow<PagingData<EpisodeModel>>

    suspend fun getAllEpisodesByIds(ids: List<Int>): List<EpisodeModel>
}