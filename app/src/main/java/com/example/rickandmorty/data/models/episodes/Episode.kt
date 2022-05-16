package com.example.rickandmorty.data.models.episodes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EPISODES_TABLE")
data class Episode(
    @PrimaryKey val id: Int,
    val name: String,
    val characters: List<String>,
    val air_date: String,
    val episode: String,
)