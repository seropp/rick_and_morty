package com.example.rickandmorty.data.repositories.episodes_repositories

import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.repositories.episodes_repositories.GetEpisodeFiltersRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeFiltersRepositoryImpl(
    private val db: RickAndMortyDatabase
) : GetEpisodeFiltersRepository {

    override fun getListEpisodes(): Flow<List<String>> =
        db.getEpisodeDao().getEpisodes()
}