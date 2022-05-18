package com.example.rickandmorty.data.storage.room.dao.page_keys_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.models.page_keys.CharactersPageKeys

@Dao
interface CharactersKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharactersKeys(remoteKeysCharacters: List<CharactersPageKeys>)

    @Query("SELECT * FROM CHARACTERS_PAGE_KEYS WHERE id =:id")
    suspend fun getCharactersRemoteKeys(id: Int): CharactersPageKeys

    @Query("DELETE FROM CHARACTERS_PAGE_KEYS")
    suspend fun deleteAllCharactersRemoteKeys()
}