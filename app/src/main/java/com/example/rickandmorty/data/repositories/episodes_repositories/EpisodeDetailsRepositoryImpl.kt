package com.example.rickandmorty.data.repositories.episodes_repositories

import android.util.Log
import com.example.rickandmorty.data.mapper.entity_to_domain_model.EpisodeEntityToDomainModel
import com.example.rickandmorty.data.models.episodes.Episode
import com.example.rickandmorty.data.remote.api.episodes.EpisodeDetailsApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodeDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class EpisodeDetailsRepositoryImpl(
    private val episodeDetailsApi: EpisodeDetailsApi,
    private val db: RickAndMortyDatabase
) : EpisodeDetailsRepository {

    override suspend fun getEpisodeById(id: Int): EpisodeModel = withContext(Dispatchers.IO) {
        try {
            val episodeFromApi: Response<Episode> =
                episodeDetailsApi.getEpisodeById(id = id)
            if (episodeFromApi.isSuccessful) {
                episodeFromApi.body()
                    ?.let { db.getEpisodeDao().insertEpisode(episode = it) }
            }

        } catch (e: HttpException) {
            Log.e("Log", "${e.code()}")
        } catch (e: IOException) {
            Log.e("Log", "${e.message}")
        }

        return@withContext EpisodeEntityToDomainModel().transform(
            db.getEpisodeDao().getEpisodeById(id = id)
        )
    }
}