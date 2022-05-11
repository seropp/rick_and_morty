package com.example.rickandmorty.domain.models.character

import androidx.room.PrimaryKey


data class CharacterModel(
    val id: Int,
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