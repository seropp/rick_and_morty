package com.example.rickandmorty.presentation.screens.locations.location_details_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersByIdsUseCase
import com.example.rickandmorty.domain.use_cases.locations.location_details_use_cases.GetLocationByIdUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetCharacterPresentationModel
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetEpisodePresentationModel
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetLocationPresentationModel
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import com.example.rickandmorty.presentation.models.location.LocationPresentation
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getAllCharactersByIdsUseCase: GetAllCharactersByIdsUseCase
) : ViewModel() {

    private val _locationDetails = MutableLiveData<LocationPresentation>()
    val locationDetails: MutableLiveData<LocationPresentation> = _locationDetails

    private val _charactersList = MutableLiveData<List<CharacterPresentation>>()
    val episodesList: MutableLiveData<List<CharacterPresentation>> = _charactersList

    fun getLocation(id: Int) {
        viewModelScope.launch {
            _locationDetails.value =
                GetLocationPresentationModel().transform(getLocationByIdUseCase.execute(id = id))
        }
    }

    fun getEpisodesList(ids: List<Int>) {
        viewModelScope.launch {
            _charactersList.value =
                getAllCharactersByIdsUseCase.execute(ids = ids).map {
                    GetCharacterPresentationModel().transform(it)
                }
        }
    }
}