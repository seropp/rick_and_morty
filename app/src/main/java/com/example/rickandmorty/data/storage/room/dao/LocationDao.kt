package com.example.rickandmorty.data.storage.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.storage.room.entities.location.LocationEntity
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
    fun insertAllLocations(locations: List<LocationEntity?>?)

    /**
     * Add location.
     * With the replacement of the same location.
     * This method is used when we request a location from the server (by id).
     *
     * @param location - Location.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: LocationEntity)

    /**
     * Get all locations with pagination.
     *
     * @return
     */
    @Query("""SELECT * FROM locations_table ORDER BY id ASC""")
    fun getAllLocations(): DataSource.Factory<Int, LocationEntity>

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
        """SELECT * FROM locations_table
        WHERE name LIKE '%' || :name || '%'
        AND type LIKE :type
        AND dimension LIKE :dimension"""
    )
    fun getFilteredCharacters(
        name: String?,
        type: String?,
        dimension: String?,
    ): Flow<List<LocationEntity>>

    /**
     * Get location by id
     *
     * @param id - Location id.
     */
    @Query("SELECT * FROM locations_table WHERE id = :id")
    fun getLocationById(id: Int): LocationEntity?

    /**
     * Get all types from db.
     *
     * @return Flow with Location's types.
     */
    @Query(
        """SELECT type FROM locations_table"""
    )
    fun getTypes(): Flow<List<String>>

    /**
     * Get all dimensions from db.
     *
     * @return Flow with Location's dimensions.
     */
    @Query(
        """SELECT dimension FROM locations_table"""
    )
    fun getDimensions(): Flow<List<String>>

    /**
     * Delete all locations from table (for mediator).
     */
    @Query("DELETE FROM locations_table")
    fun clearAllLocations()
}