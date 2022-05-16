package com.example.rickandmorty.data.storage.room.dao.page_keys_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.models.page_keys.LocationsPageKeys

@Dao
interface LocationsKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocationKeys(remoteKeysLocations: List<LocationsPageKeys>)

    @Query("SELECT * FROM LOCATIONS_PAGE_KEYS WHERE id =:id")
    suspend fun getLocationRemoteKeys(id: Int): LocationsPageKeys

    @Query("DELETE FROM LOCATIONS_PAGE_KEYS")
    suspend fun deleteAllLocationRemoteKeys()
}