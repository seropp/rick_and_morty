package com.example.rickandmorty.data.storage.sharedPref

import android.content.Context

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val EPISODES = "EPISODES"

class EpisodeSettingsPref(context: Context) : SharedPrefStorage {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun save(episodes: Map<String, List<String>>) {
        val episodes = episodes.getValue("episodes").joinToString(separator = "\n")
        sharedPreferences.edit().putString(EPISODES, episodes).apply()
    }

    override suspend fun get(): Map<String, List<String>> {
        val episodes: List<String> =
            (sharedPreferences.getString(EPISODES, "S01E01") ?: "S01E01").lines()
        return mapOf(
            "episodes" to episodes
        )
    }
}