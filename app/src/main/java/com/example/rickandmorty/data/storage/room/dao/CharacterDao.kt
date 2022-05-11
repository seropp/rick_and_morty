package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
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
    suspend fun insertAllCharacters(characters: List<CharacterEntity?>?)

    /**
     * Add character.
     * With the replacement of the same character.
     * This method is used when we request a character from the server (by id).
     *
     * @param character - Character.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    /**
     * Get all characters with pagination.
     *
     * @return
     */
    @Query("""SELECT * FROM characters_table ORDER BY id ASC""")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

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
        """SELECT * FROM characters_table
        WHERE name LIKE '%' || :name || '%'
        AND status LIKE :status
        AND gender LIKE :gender
        AND type LIKE :type
        AND species LIKE :species"""
    )
    fun getFilteredCharacters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?,
    ): PagingSource<Int, CharacterEntity>

    /**
     * Get all characters by ids without pagination.
     *
     * @param ids - Characters ids.
     *
     * @return
     */
    @Query(
        """SELECT * FROM characters_table
        WHERE id IN (:ids)
        ORDER BY id ASC"""
    )
    fun getCharactersByIds(ids: List<Int>): Flow<List<CharacterEntity>>

    /**
     * Get character by id
     *
     * @param id - Character id.
     */
    @Query("SELECT * FROM characters_table WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    /**
     * Get all types from db.
     *
     * @return Flow with Character's types.
     */
    @Query(
        """SELECT type FROM characters_table"""
    )
    fun getTypes(): Flow<List<String>>

    /**
     * Get all species from db.
     *
     * @return Flow with Character's species.
     */
    @Query(
        """SELECT species FROM characters_table"""
    )
    fun getSpecies(): Flow<List<String>>

    /**
     * Delete all characters from table (for mediator).
     */
    @Query("DELETE FROM characters_table")
   fun clearAllCharacters()
}