package com.example.rickandmorty.data.storage.sharedPref

import android.content.Context

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val LOCATION_TYPE = "location_types"
private const val LOCATION_DIMENSION = "location_dimensions"

class LocationSettingsPref(context: Context) : SharedPrefStorage {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun save(settingsList: Map<String, List<String>>) {
        val types = settingsList.getValue("type").joinToString(separator = "\n")
        val dimensions = settingsList.getValue("dimension").joinToString(separator = "\n")
        sharedPreferences.edit().putString(LOCATION_TYPE, types).apply()
        sharedPreferences.edit().putString(LOCATION_DIMENSION, dimensions).apply()
    }

    override suspend fun get(): Map<String, List<String>> {
        val types: List<String> =
            (sharedPreferences.getString(LOCATION_TYPE, "unknown") ?: "unknown").lines()
        val dimensions: List<String> =
            (sharedPreferences.getString(LOCATION_DIMENSION, "unknown") ?: "unknown").lines()
        return mapOf(
            "type" to types,
            "dimension" to dimensions
        )
    }
}