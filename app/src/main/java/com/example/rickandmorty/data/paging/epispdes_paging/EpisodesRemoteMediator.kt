package com.example.rickandmorty.data.paging.epispdes_paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.episodes.Episode
import com.example.rickandmorty.data.models.page_keys.EpisodesPageKeys
import com.example.rickandmorty.data.remote.api.episodes.EpisodesApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class EpisodesRemoteMediator(
    private val episodesApi: EpisodesApi,
    private val db: RickAndMortyDatabase
) : RemoteMediator<Int, Episode>() {

    private val episodesDao = db.getEpisodeDao()
    private val keyDao = db.getEpisodesKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response: Response<PagedResponse<Episode>> =
                episodesApi.getEpisodePage(page = currentPage)

            val resBody = response.body()
            val episodes = resBody?.results
            val endOfPaginationReached = episodes?.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached == true) null else currentPage + 1



            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    episodesDao.deleteAllEpisodes()
                    keyDao.deleteAllEpisodesRemoteKeys()
                }
                val keys = episodes?.map { episode ->
                    EpisodesPageKeys(
                        id = episode.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { keyDao.insertAllEpisodesKeys(remoteKeysEpisodes = it) }
                episodes?.let { episodesDao.insertAllEpisodes(episodes = it) }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Episode>
    ): EpisodesPageKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keyDao.getEpisodesRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Episode>
    ): EpisodesPageKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { episode ->
                keyDao.getEpisodesRemoteKeys(id = episode.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Episode>
    ): EpisodesPageKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { episode ->
                keyDao.getEpisodesRemoteKeys(id = episode.id)
            }
    }
}