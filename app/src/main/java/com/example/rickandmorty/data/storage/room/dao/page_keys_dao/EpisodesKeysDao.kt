package com.example.rickandmorty.data.storage.room.dao.page_keys_dao

import androidx.room.*
import com.example.rickandmorty.data.models.page_keys.EpisodesPageKeys

@Dao
interface EpisodesKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodesKeys(remoteKeysEpisodes: List<EpisodesPageKeys>?)

    @Query("SELECT * FROM EPISODES_PAGE_KEYS WHERE id =:id")
    suspend fun getEpisodesRemoteKeys(id: Int): EpisodesPageKeys

    @Query("DELETE FROM EPISODES_PAGE_KEYS")
    suspend fun deleteAllEpisodesRemoteKeys()
}