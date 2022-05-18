package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmorty.data.models.location.Location
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    /**
     * Add all locations.
     * With the replacement of the same locations.
     * This method is used when we request a list of locations from the server.
     *
     * @param locations - Locations List.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLocations(locations: List<Location?>?)

    /**
     * Delete all locations for pagination.
     *
     * @return
     */
    @Query("DELETE FROM LOCATIONS_TABLE")
    suspend fun deleteAllLocations()

    /**
     * Add location.
     * With the replacement of the same location.
     * This method is used when we request a location from the server (by id).
     *
     * @param location - Location.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    /**
     * Get all filtered locations with pagination. (name, status, gender, type, species).
     *
     * @param name - Location name.
     * @param type - Location type.
     * @param dimension - Location species.
     *
     * @return
     */
    @Query(
        """SELECT * FROM LOCATIONS_TABLE
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:type IS NULL OR type LIKE :type)
        AND (:dimension IS NULL OR dimension LIKE :dimension)"""
    )
    fun getFilteredLocations(
        name: String?,
        type: String?,
        dimension: String?,
    ):  PagingSource<Int, Location>

    /**
     * Get location by id
     *
     * @param id - Location id.
     */
    @Query("SELECT * FROM LOCATIONS_TABLE WHERE id = :id")
    suspend fun getLocationById(id: Int): Location

    /**
     * Get all types from db.
     *
     * @return Flow with Location's types.
     */
    @Query(
        """SELECT type FROM LOCATIONS_TABLE ORDER BY type ASC"""
    )
    fun getTypes(): Flow<List<String>>

    /**
     * Get all dimensions from db.
     *
     * @return Flow with Location's dimensions.
     */
    @Query(
        """SELECT dimension FROM LOCATIONS_TABLE ORDER BY dimension ASC"""
    )
    fun getDimensions(): Flow<List<String>>
}