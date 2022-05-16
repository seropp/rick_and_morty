package com.example.rickandmorty.presentation.models.character


data class CharacterPresentation(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originLocation: Map<String, String>,
    val lastLocation: Map<String, String>,
    val imageUrl: String,
    val episodeIds: List<Int>
)