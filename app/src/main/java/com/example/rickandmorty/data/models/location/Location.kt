package com.example.rickandmorty.data.models.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "LOCATIONS_TABLE")
data class Location(
    @PrimaryKey var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    @Ignore
    val residents: List<String>?,
) {
    @ColumnInfo(name = "residentsIds")
    var residentsIds: List<Int>? = null
        get() {
            return residents?.mapNotNull { residents ->
                residents.dropWhile { char ->
                    !char.isDigit()
                }.toIntOrNull()
            }
        }

    constructor(
    ) : this(-1, "", "", "", null)
}

// data class LocationDto(
//    val created: String,
//    val dimension: String,
//    val id: Int,
//    val name: String,
//    val residents: List<String>,
//    val type: String,
//    val url: String
//)

//@Entity(tableName = "locations_table")
//data class LocationEntity(
//    @PrimaryKey val id: Int,
//    val name: String,
//    val type: String,
//    val dimension: String,
//    val residentsIds: List<Int>
//)