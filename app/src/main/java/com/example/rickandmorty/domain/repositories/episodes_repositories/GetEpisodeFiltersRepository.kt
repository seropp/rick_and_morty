package com.example.rickandmorty.domain.repositories.episodes_repositories

import kotlinx.coroutines.flow.Flow

interface GetEpisodeFiltersRepository {

    fun getListEpisodes(): Flow<List<String>>
}