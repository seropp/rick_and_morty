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
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class EpisodesRemoteMediator(
    private val episodesApi: EpisodesApi,
    private val db: RickAndMortyDatabase,
    private val name: String?,
    private val episode: String?
) : RemoteMediator<Int, Episode>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private val episodesDao = db.getEpisodeDao()
    private val keyDao = db.getEpisodesKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): MediatorResult {


        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {

            val response: PagedResponse<Episode> =
                episodesApi.getEpisodes(
                    page = page,
                    name = name,
                    episode = episode
                )

            val isEndOfList =
                response.info.next == null
                        || response.toString().contains("error")
                        || response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    episodesDao.deleteAllEpisodes()
                    keyDao.deleteAllEpisodesRemoteKeys()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    EpisodesPageKeys(it.id, prevPage = prevKey, nextPage = nextKey)
                }
                keyDao.insertAllEpisodesKeys(remoteKeysEpisodes = keys)
                episodesDao.insertAllEpisodes(episodes = response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
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
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { it ->
                keyDao.getEpisodesRemoteKeys(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Episode>
    ): EpisodesPageKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let {it ->
                keyDao.getEpisodesRemoteKeys(id = it.id)
            }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                return nextPage ?: RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null)
            }
        }
    }
}