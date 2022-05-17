package com.example.rickandmorty.presentation.models.location

import kotlinx.android.parcel.Parcelize

data class LocationPresentation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: List<Int>
)