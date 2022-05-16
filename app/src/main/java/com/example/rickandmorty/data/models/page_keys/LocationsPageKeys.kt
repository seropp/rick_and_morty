package com.example.rickandmorty.data.models.page_keys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LOCATIONS_PAGE_KEYS")
data class LocationsPageKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)