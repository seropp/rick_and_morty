package com.example.rickandmorty.data.models.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CHARACTERS_TABLE")
data class Characters(
    @PrimaryKey var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var image: String,

    @Ignore
    private var origin: LinkedLocation?,
    @Ignore
    private var location: LinkedLocation?,
    @Ignore
    private var episode: List<String>?,

    ) {
    @ColumnInfo(name = "episodeIds")
    var episodeIds: List<Int>? = null
        get() {
            return episode?.mapNotNull { episodeUrl ->
                episodeUrl.dropWhile { char ->
                    !char.isDigit()
                }.toIntOrNull()
            }
        }

    @ColumnInfo(name = "locationId")
    var locationId: Int? = null
        get() {
            return location?.url?.substringAfterLast("/")?.toIntOrNull()
        }

    @ColumnInfo(name = "originId")
    var originId: Int? = null
        get() {
            return origin?.url?.substringAfterLast("/")?.toIntOrNull()
        }

    constructor(
    ) : this(
        -1, "", "", "", "", "", "", null, null, null
    )
}