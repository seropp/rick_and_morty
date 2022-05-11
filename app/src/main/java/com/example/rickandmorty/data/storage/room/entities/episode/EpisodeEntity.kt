package com.example.rickandmorty.data.storage.room.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes_table")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val residentsIds: List<Int>,
)