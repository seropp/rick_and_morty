package com.example.rickandmorty.domain.repositories.episodes_repositories

import com.example.rickandmorty.domain.models.episode.EpisodeModel

interface EpisodeDetailsRepository {

    suspend fun getEpisodeById(id: Int): EpisodeModel
}