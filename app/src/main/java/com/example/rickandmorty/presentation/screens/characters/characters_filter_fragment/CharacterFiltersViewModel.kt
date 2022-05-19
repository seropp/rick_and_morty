package com.example.rickandmorty.presentation.screens.characters.characters_filter_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersSpeciesUseCase
import com.example.rickandmorty.domain.use_cases.characters.character_filters_use_case.GetListCharactersTypesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterFiltersViewModel(
    private val getListCharactersSpeciesUseCase: GetListCharactersSpeciesUseCase,
    private val getListCharactersTypesUseCase: GetListCharactersTypesUseCase,
): ViewModel() {

    private val _speciesList = MutableStateFlow<List<String>>(listOf())
    val speciesList: StateFlow<List<String>> = _speciesList

    private val _typeList = MutableStateFlow<List<String>>(listOf())
    val typeList: StateFlow<List<String>> = _typeList

    init {
        viewModelScope.launch {
            getListCharactersSpeciesUseCase.execute()
                .collect { list ->
                    _speciesList.value = list
                }
        }

        viewModelScope.launch {
            getListCharactersTypesUseCase.execute()
                .collect { list ->
                    _typeList.value = list
                }
        }
    }
}