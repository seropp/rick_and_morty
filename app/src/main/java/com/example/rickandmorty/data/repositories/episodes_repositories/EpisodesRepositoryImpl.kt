package com.example.rickandmorty.data.repositories.episodes_repositories

import android.util.Log
import androidx.paging.*
import com.example.rickandmorty.data.mapper.entity_to_domain_model.EpisodeEntityToDomainModel
import com.example.rickandmorty.data.models.episodes.Episode
import com.example.rickandmorty.data.paging.epispdes_paging.EpisodesRemoteMediator
import com.example.rickandmorty.data.remote.api.episodes.EpisodeDetailsApi
import com.example.rickandmorty.data.remote.api.episodes.EpisodesApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@ExperimentalPagingApi
class EpisodesRepositoryImpl(
    private val episodesApi: EpisodesApi,
    private val episodeDetailsApi: EpisodeDetailsApi,
    private val db: RickAndMortyDatabase
) : EpisodesRepository {

    override fun getAllEpisodes(
        name: String?,
        episode: String?
    ): Flow<PagingData<EpisodeModel>> {

        val pagingSourceFactory =
            {
                db.getEpisodeDao().getFilteredEpisodes(
                    name = name,
                    episode = episode
                )
            }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = EpisodesRemoteMediator(
                episodesApi = episodesApi,
                db = db,
                name = name,
                episode = episode
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it ->
                EpisodeEntityToDomainModel().transform(it)
            }
        }
    }

    override suspend fun getAllEpisodesByIds(ids: List<Int>): List<EpisodeModel> =

        withContext(Dispatchers.IO) {
            try {
                if (ids.size > 1) {
                    val idsString: String = ids.joinToString(separator = ",")
                    val episodesFromApi: Response<List<Episode>> =
                        episodesApi.getEpisodesByIds(ids = idsString)
                    if (episodesFromApi.isSuccessful) {
                        episodesFromApi.body()
                            ?.let { db.getEpisodeDao().insertAllEpisodes(episodes = it) }
                    }
                }
                if (ids.size == 1) {
                    val episodeFromApi: Response<Episode> =
                        episodeDetailsApi.getEpisodeById(id = ids[0])
                    if (episodeFromApi.isSuccessful) {
                        episodeFromApi.body()
                            ?.let { db.getEpisodeDao().insertEpisode(episode = it) }
                    }
                }

            } catch (e: HttpException) {
                Log.e("Log", "${e.code()}")
            } catch (e: IOException) {
                Log.e("Log", "${e.message}")
            }

            return@withContext db.getEpisodeDao().getEpisodesByIds(ids = ids).map {
                EpisodeEntityToDomainModel().transform(it)
            }
        }
}