package com.example.rickandmorty.data.models

data class PagedResponse<T>(
    val results: List<T> = listOf()
)