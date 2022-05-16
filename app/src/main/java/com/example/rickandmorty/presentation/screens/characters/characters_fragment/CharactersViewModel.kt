package com.example.rickandmorty.presentation.screens.characters.characters_fragment

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map

import com.example.rickandmorty.domain.use_cases.characters.characters_usecases.GetAllCharactersUseCase
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersListener
import com.example.rickandmorty.presentation.mapper.domain_model_to_presentation.GetCharacterPresentationModel
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@ExperimentalPagingApi
class CharactersViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel(), CharactersListener {

    private var _charactersFlow: Flow<PagingData<CharacterPresentation>> =
        getAllCharactersUseCase.execute().map { pagingData ->
            pagingData.map { it ->
                GetCharacterPresentationModel().transform(it)
            }
        }.cachedIn(viewModelScope)

    val charactersFlow: Flow<PagingData<CharacterPresentation>> = _charactersFlow

    override fun onItemClick(id: Int) {
        val a = 3
    }
}