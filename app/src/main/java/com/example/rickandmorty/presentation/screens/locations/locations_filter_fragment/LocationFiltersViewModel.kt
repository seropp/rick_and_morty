package com.example.rickandmorty.presentation.screens.locations.locations_filter_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.use_cases.locations.location_filters_use_cases.GetListLocationsDimensionsUseCase
import com.example.rickandmorty.domain.use_cases.locations.location_filters_use_cases.GetListLocationsTypesUseCase
import com.example.rickandmorty.domain.use_cases.settings.LocationsSettingsUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocationFiltersViewModel(
    private val getListLocationsDimensionsUseCase: GetListLocationsDimensionsUseCase,
    private val getListLocationsTypesUseCase: GetListLocationsTypesUseCase,
    private val locationSettingsUseCases: LocationsSettingsUseCases
) : ViewModel() {

    private val _dimensionList = MutableStateFlow<List<String>>(listOf())
    val dimensionList: StateFlow<List<String>> = _dimensionList

    private val _typeList = MutableStateFlow<List<String>>(listOf())
    val typeList: StateFlow<List<String>> = _typeList

    init {
        viewModelScope.launch {
            getListLocationsDimensionsUseCase.execute()
                .collect { list ->
                    _dimensionList.value = list
                }
        }

        viewModelScope.launch {
            getListLocationsTypesUseCase.execute()
                .collect { list ->
                    _typeList.value = list
                }
        }
    }

    private val _settings = MutableLiveData<Map<String, List<String>>>(null)
    private val settings: LiveData<Map<String, List<String>>> = _settings

    fun saveListEpisodes(settings: Map<String, List<String>>) {
        viewModelScope.launch {
            locationSettingsUseCases.saveLocationTypes(settings)
        }
    }

    fun getListEpisodes() {
        viewModelScope.launch {
            _settings.value = locationSettingsUseCases.getLocationTypes()
        }
    }
}