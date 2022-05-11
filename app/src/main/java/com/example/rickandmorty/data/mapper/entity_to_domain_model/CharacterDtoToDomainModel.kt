package com.example.rickandmorty.data.mapper.entity_to_domain_model

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.remote.dto.characterDto.CharacterDto
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import com.example.rickandmorty.domain.models.character.CharacterModel

class CharacterDtoToDomainModel :
    Mapper<CharacterDto, CharacterModel> {

    override fun transform(data: CharacterDto): CharacterModel {

        val episodeIds: List<Int> = data.episode.mapNotNull { episodeUrl ->
            episodeUrl.dropWhile { char ->
                !char.isDigit()
            }.toIntOrNull()
        }

        val lastLocationId: Int? =
            data.location.url.substringAfterLast("/").toIntOrNull()

        val originId: Int? =
            data.origin.url.substringAfterLast("/").toIntOrNull()

        return CharacterModel(
            id = data.id,
            name = data.name,
            species = data.species,
            status = data.status,
            type = data.type,
            gender = data.gender,
            originLocation = originId,
            lastLocation =lastLocationId,
            imageUrl = data.image,
            episodeIds = episodeIds,
            page = data.page
        )
    }
}