package com.example.rickandmorty.presentation.mapper.domain_model_to_presentation

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.domain.models.episode.EpisodeModel
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation

class GetEpisodePresentationModel :
    Mapper<EpisodeModel, EpisodePresentation> {

    override fun transform(data: EpisodeModel): EpisodePresentation {

        return EpisodePresentation(
            id = data.id,
            name = data.name,
            episode = data.episode,
            air_date = data.air_date,
            residentsIds = data.residentsIds
        )
    }
}