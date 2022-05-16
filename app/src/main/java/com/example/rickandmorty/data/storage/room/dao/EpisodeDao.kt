package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmorty.data.models.episodes.Episode
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
    suspend fun insertAllEpisodes(episodes: List<Episode?>?)

    /**
     * Get all episodes with pagination.
     *
     * @return
     */
    @Query("SELECT * FROM EPISODES_TABLE")
    fun getAllEpisodes(): PagingSource<Int, Episode>

    /**
     * Delete all episodes for pagination.
     *
     * @return
     */
    @Query("DELETE FROM EPISODES_TABLE")
    suspend fun deleteAllEpisodes()

    /**
     * Add episode.
     * With the replacement of the same episode.
     * This method is used when we request a episode from the server (by id).
     *
     * @param episode - Episode.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    /**
     * Get all filtered episodes with pagination. (name, episode).
     *
     * @param name - Episode name.
     * @param episode - Episode status.
     *
     * @return
     */
    @Query(
        """SELECT * FROM EPISODES_TABLE
        WHERE name LIKE '%' || :name || '%'
        AND episode LIKE :episode"""
    )
    fun getFilteredEpisodes(
        name: String?,
        episode: String?,
    ): Flow<List<Episode>>

    /**
     * Get all episodes by ids without pagination.
     *
     * @param ids - Episodes ids.
     *
     * @return
     */
    @Query(
        """SELECT * FROM EPISODES_TABLE
        WHERE id IN (:ids)
        ORDER BY id ASC"""
    )
    suspend fun getEpisodesByIds(ids: List<Int>): List<Episode>

    /**
     * Get episode by id
     *
     * @param id - Episode id.
     */
    @Query("SELECT * FROM EPISODES_TABLE WHERE id = :id")
    suspend fun getEpisodeById(id: Int): Episode

    /**
     * Get all episodes from db.
     *
     * @return Flow with Episode's types.
     */
    @Query(
        """SELECT episode FROM EPISODES_TABLE ORDER BY episode ASC"""
    )
    fun getEpisodes(): Flow<List<String>>
}