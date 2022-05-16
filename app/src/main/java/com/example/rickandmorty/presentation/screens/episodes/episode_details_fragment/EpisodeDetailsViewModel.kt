package com.example.rickandmorty.presentation.screens.episodes.episode_details_fragment

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.domain.use_cases.episodes.episode_details_use_cases.GetEpisodeByIdUseCase

class EpisodeDetailsViewModel(
    private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase
): ViewModel() {

}