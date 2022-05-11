package com.example.rickandmorty.data.storage.room.entities.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originLocation: Int?,
    val lastLocation: Int?,
    val imageUrl: String,
    val episodeIds: List<Int>,
    var page: Int?
)