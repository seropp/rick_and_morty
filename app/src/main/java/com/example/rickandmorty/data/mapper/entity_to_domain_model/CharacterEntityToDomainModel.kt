package com.example.rickandmorty.data.mapper.entity_to_domain_model

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.models.characters.Characters
import com.example.rickandmorty.domain.models.character.CharacterModel

class CharacterEntityToDomainModel :
    Mapper<Characters, CharacterModel> {

    override fun transform(data: Characters): CharacterModel {

        return CharacterModel(
            id = data.id,
            name = data.name,
            species = data.species,
            status = data.status,
            type = data.type,
            gender = data.gender,
            originLocation = data.originId,
            lastLocation =data.locationId,
            imageUrl = data.image,
            episodeIds = data.episodeIds,
        )
    }
}