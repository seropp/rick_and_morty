package com.example.rickandmorty.data.storage.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page_key")
data class PageKeyEntity(
    @PrimaryKey val id: Int,
    val nextPageUrl: String?
)