package com.example.rickandmorty.data.remote.dto.pagesInfo

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)