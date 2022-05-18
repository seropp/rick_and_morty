package com.example.rickandmorty.data.repositories.settings_repositories

import com.example.rickandmorty.data.storage.sharedPref.LocationSettingsPref
import com.example.rickandmorty.domain.repositories.settings_repositories.LocationSettingsRepository

class LocationSettingsRepositoryImpl(
    private val locationSettingsPref: LocationSettingsPref
) : LocationSettingsRepository {

    override suspend fun saveLocationSettings(settingsList: Map<String, List<String>>) {
        locationSettingsPref.save(settingsList = settingsList)
    }

    override suspend fun getLocationSettings(): Map<String, List<String>> {
        return locationSettingsPref.get()
    }
}