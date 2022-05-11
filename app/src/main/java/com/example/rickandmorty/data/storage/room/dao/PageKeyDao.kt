package com.example.rickandmorty.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.storage.room.entities.PageKeyEntity

@Dao
interface PageKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pageKeyEntity: PageKeyEntity)

    @Query("SELECT * FROM page_key WHERE id = :id")
    suspend fun getNextPageKey(id: Int): PageKeyEntity?

    @Query("DELETE FROM page_key")
    suspend fun clearAll()
}