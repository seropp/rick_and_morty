package com.example.rickandmorty.domain.use_cases.settings

import com.example.rickandmorty.domain.repositories.settings_repositories.LocationSettingsRepository

class LocationsSettingsUseCases(private val locationSettingsRepository: LocationSettingsRepository) {

    suspend fun saveLocationTypes(types: Map<String, List<String>>) {
        locationSettingsRepository.saveLocationSettings(types = types)
    }

    suspend fun getLocationTypes(): Map<String, List<String>> {
        return locationSettingsRepository.getLocationSettings()
    }
}