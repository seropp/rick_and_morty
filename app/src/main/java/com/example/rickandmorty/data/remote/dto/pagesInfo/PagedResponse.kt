package com.example.rickandmorty.data.remote.dto.pagesInfo

import android.graphics.pdf.PdfDocument
import com.google.gson.annotations.SerializedName


data class PagedResponse<T>(
    @SerializedName("info")  val pageInfo: PageInfo,
    val results: List<T> = listOf()
)