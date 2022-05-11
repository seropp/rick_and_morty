package com.example.rickandmorty.data.storage.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converter {
    @JvmStatic
    @TypeConverter
    fun listToString(list: List<Int>?): String? {
        return Gson().toJson(list)
    }

    @JvmStatic
    @TypeConverter
    fun stringToList(json: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, listType)
    }
}