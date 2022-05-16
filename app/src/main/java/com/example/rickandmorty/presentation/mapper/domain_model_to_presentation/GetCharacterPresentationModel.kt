package com.example.rickandmorty.presentation.mapper.domain_model_to_presentation

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.presentation.models.character.CharacterPresentation

class GetCharacterPresentationModel :
    Mapper<CharacterModel, CharacterPresentation> {

    override fun transform(data: CharacterModel): CharacterPresentation {

        return CharacterPresentation(
            id = data.id,
            name = data.name,
            species = data.species,
            status = data.status,
            type = data.type,
            gender = data.gender,
            originLocation = data.originLocation,
            lastLocation = data.lastLocation,
            imageUrl = data.imageUrl,
            episodeIds = data.episodeIds,
        )
    }
}