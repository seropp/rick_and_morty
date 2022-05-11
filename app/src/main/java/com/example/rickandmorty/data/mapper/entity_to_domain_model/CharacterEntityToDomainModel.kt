package com.example.rickandmorty.data.mapper.entity_to_domain_model

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import com.example.rickandmorty.domain.models.character.CharacterModel

class CharacterEntityToDomainModel :
    Mapper<CharacterEntity, CharacterModel> {

    override fun transform(data: CharacterEntity): CharacterModel {

        return CharacterModel(
            id = data.id,
            name = data.name,
            species = data.species,
            status = data.status,
            type = data.type,
            gender = data.gender,
            originLocation = data.originLocation,
            lastLocation =data. lastLocation,
            imageUrl = data.imageUrl,
            episodeIds = data.episodeIds,
            page = data.page
        )
    }
}