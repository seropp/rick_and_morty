package com.example.rickandmorty.data.paging.locations_paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.models.PagedResponse
import com.example.rickandmorty.data.models.location.Location
import com.example.rickandmorty.data.models.page_keys.CharactersPageKeys
import com.example.rickandmorty.data.models.page_keys.LocationsPageKeys
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.remote.api.locations.LocationsApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class LocationsRemoteMediator(
    private val locationsApi: LocationsApi,
    private val db: RickAndMortyDatabase
) : RemoteMediator<Int, Location>() {

    private val locationDao = db.getLocationDao()
    private val keyDao = db.getLocationsKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Location>
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

            val response: Response<PagedResponse<Location>> =
                locationsApi.getLocationPage(page = currentPage)

            val resBody = response.body()
            val locations = resBody?.results
            val endOfPaginationReached = locations?.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    locationDao.deleteAllLocations()
                    keyDao.deleteAllLocationRemoteKeys()
                }
                val keys = locations?.map { location ->
                    LocationsPageKeys(
                        id = location.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { keyDao.insertAllLocationKeys(remoteKeysLocations = it) }
                locations?.let { locationDao.insertAllLocations(locations = it) }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Location>
    ): LocationsPageKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keyDao.getLocationRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Location>
    ): LocationsPageKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { location ->
                keyDao.getLocationRemoteKeys(id = location.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Location>
    ): LocationsPageKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { location ->
                keyDao.getLocationRemoteKeys(id = location.id)
            }
    }
}