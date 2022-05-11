package com.example.rickandmorty.domain.models.location




data class LocationModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Int>?
)