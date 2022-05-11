package com.example.rickandmorty.domain.models.episode

import kotlinx.android.parcel.Parcelize


data class EpisodeModel(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val residentsIds: List<Int>,
)