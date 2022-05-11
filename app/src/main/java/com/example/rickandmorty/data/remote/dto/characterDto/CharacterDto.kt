package com.example.rickandmorty.data.remote.dto.characterDto

import kotlinx.android.parcel.Parcelize


data class CharacterDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LinkedLocation,
    val name: String,
    val origin: LinkedLocation,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var page: Int?
)