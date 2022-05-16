package com.example.rickandmorty.data.models.episodes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "EPISODES_TABLE")
data class Episode(
    @PrimaryKey var id: Int,
    var name: String,
    @Ignore
    var characters: List<String>?,
    var air_date: String,
    var episode: String,
) {

    @ColumnInfo(name = "residentsIds")
    var residentsIds: List<Int>? = null
        get() {
            return characters?.mapNotNull { characters ->
                characters.dropWhile { char ->
                    !char.isDigit()
                }.toIntOrNull()
            }
        }

    constructor(
    ) : this(-1, "", null, "", "")
}

//data class EpisodeDto(
//    val air_date: String,
//    val characters: List<String>,
//    val created: String,
//    val episode: String,
//    val id: Int,
//    val name: String,
//    val url: String
