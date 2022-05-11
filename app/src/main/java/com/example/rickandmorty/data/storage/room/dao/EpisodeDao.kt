package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.storage.room.entities.episode.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    /**
     * Add all episodes.
     * With the replacement of the same episodes.
     * This method is used when we request a list of episodes from the server.
     *
     * @param episodes - Episodes List.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEpisodes(episodes: List<EpisodeEntity?>?)

    /**
     * Add episode.
     * With the replacement of the same episode.
     * This method is used when we request a episode from the server (by id).
     *
     * @param episode - Episode.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: EpisodeEntity)

    /**
     * Get all episodes with pagination.
     *
     * @return
     */
    @Query("""SELECT * FROM episodes_table ORDER BY id ASC""")
    fun getAllEpisodes(): DataSource.Factory<Int, EpisodeEntity>

    /**
     * Get all episodes by ids without pagination.
     *
     * @param ids - Episodes ids.
     *
     * @return
     */
    @Query(
        """SELECT * FROM episodes_table
        WHERE id IN (:ids)
        ORDER BY id ASC"""
    )
    fun getEpisodesByIds(ids: List<Int>): List<EpisodeEntity>

    /**
     * Get all filtered episodes with pagination. (name, episode).
     *
     * @param name - Episode name.
     * @param episode - Episode status.
     *
     * @return
     */
    @Query(
        """SELECT * FROM episodes_table
        WHERE name LIKE '%' || :name || '%'
        AND episode LIKE :episode"""
    )
    fun getFilteredCharacters(
        name: String?,
        episode: String?,
    ): Flow<List<EpisodeEntity>>

    /**
     * Get episode by id
     *
     * @param id - Episode id.
     */
    @Query("SELECT * FROM episodes_table WHERE id = :id")
    fun getEpisodeById(id: Int): EpisodeEntity?

    /**
     * Get all episodes from db.
     *
     * @return Flow with Episode's types.
     */
    @Query(
        """SELECT episode FROM episodes_table"""
    )
    fun getEpisodes(): Flow<List<String>>

    /**
     * Delete all episodes from table (for mediator).
     */
    @Query("DELETE FROM episodes_table")
    fun clearAllEpisodes()
}