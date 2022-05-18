package com.example.rickandmorty.domain.repositories.settings_repositories

interface LocationSettingsRepository {
    suspend fun saveLocationSettings(types: Map<String, List<String>>)

    suspend fun getLocationSettings(): Map<String, List<String>>

}