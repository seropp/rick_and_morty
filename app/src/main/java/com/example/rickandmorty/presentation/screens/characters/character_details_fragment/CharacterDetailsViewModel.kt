package com.example.rickandmorty.presentation.screens.characters.character_details_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.use_cases.characters.character_details_use_cases.GetCharacterByIdUseCase
import com.example.rickandmorty.domain.use_cases.episodes.episodes_usecases.GetAllEpisodesByIdsUseCase
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetCharacterPresentationModel
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetEpisodePresentationModel
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getAllEpisodesByIdsUseCase: GetAllEpisodesByIdsUseCase
) : ViewModel() {

    private val _characterDetails = MutableLiveData<CharacterPresentation>()
    val characterDetails: MutableLiveData<CharacterPresentation> = _characterDetails

    private val _episodesList = MutableLiveData<List<EpisodePresentation>>()
    val episodesList: MutableLiveData<List<EpisodePresentation>> = _episodesList

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _characterDetails.value =
                GetCharacterPresentationModel().transform(getCharacterByIdUseCase.execute(id = id))
        }
    }

    fun getEpisodesList(ids: List<Int>) {
        viewModelScope.launch() {
            kotlin.runCatching {


            }.onSuccess { it ->
                _isLoading.postValue(false)
                _episodesList.value = getAllEpisodesByIdsUseCase.execute(ids = ids).map {
                    GetEpisodePresentationModel().transform(it)
                }
            }.onFailure {
                _isLoading.postValue(false)
            }
        }
    }

}