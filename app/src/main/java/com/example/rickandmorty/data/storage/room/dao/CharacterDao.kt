package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmorty.data.models.characters.Characters
import kotlinx.coroutines.flow.Flow


@Dao
interface CharacterDao {

    /**
     * Add all characters.
     * With the replacement of the same characters.
     * This method is used when we request a list of characters from the server.
     *
     * @param characters - Character List.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<Characters?>?)

    /**
     * Get all characters with pagination.
     *
     * @return
     */
    @Query("SELECT * FROM CHARACTERS_TABLE")
    fun getAllCharacters(): Flow<List <Characters>>

    /**
     * Delete all characters for pagination.
     *
     * @return
     */
    @Query("DELETE FROM CHARACTERS_TABLE")
    suspend fun deleteAllCharacters()

    /**
     * Add character.
     * With the replacement of the same character.
     * This method is used when we request a character from the server (by id).
     *
     * @param character - Character.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Characters)

    /**
     * Get all filtered characters with pagination. (name, status, gender, type, species).
     *
     * @param name - Character name.
     * @param status - Character status.
     * @param gender - Character gender.
     * @param type - Character type.
     * @param species - Character species.
     *
     * @return
     */
    @Query(
        """SELECT * FROM CHARACTERS_TABLE
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status LIKE :status)
        AND (:gender IS NULL OR gender LIKE :gender)
        AND (:type IS NULL OR type LIKE :type)
        AND (:species IS NULL OR species LIKE :species)"""
    )
    fun getFilteredCharacters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?,
    ): PagingSource<Int, Characters>

    /**
     * Get all characters by ids without pagination.
     *
     * @param ids - Characters ids.
     *
     * @return
     */
    @Query(
        """SELECT * FROM CHARACTERS_TABLE
        WHERE id IN (:ids)
        ORDER BY id ASC"""
    )
    suspend fun getCharactersByIds(ids: List<Int>): List<Characters>

    /**
     * Get character by id
     *
     * @param id - Character id.
     */
    @Query("SELECT * FROM CHARACTERS_TABLE WHERE id = :id")
    suspend fun getCharacterById(id: Int): Characters

    /**
     * Get all types from db.
     *
     * @return Flow with Character's types.
     */
    @Query(
        """SELECT type FROM CHARACTERS_TABLE ORDER BY type ASC"""
    )
    fun getTypes(): Flow<List<String>>

    /**
     * Get all species from db.
     *
     * @return Flow with Character's species.
     */
    @Query(
        """SELECT species FROM CHARACTERS_TABLE ORDER BY species ASC"""
    )
    fun getSpecies(): Flow<List<String>>
}