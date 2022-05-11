package com.example.rickandmorty.data.mapper.dto_to_entity

import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.remote.dto.characterDto.CharacterDto

class CharacterDtoToEntity :
    Mapper<CharacterDto, CharacterEntity> {

    override fun transform(data: CharacterDto): CharacterEntity {

        val episodeIds: List<Int> = data.episode.mapNotNull { episodeUrl ->
            episodeUrl.dropWhile { char ->
                !char.isDigit()
            }.toIntOrNull()
        }

        val lastLocationId: Int? =
            data.location.url.substringAfterLast("/").toIntOrNull()

        val originId: Int? =
            data.origin.url.substringAfterLast("/").toIntOrNull()

        return CharacterEntity(
            id = data.id,
            name = data.name,
            species = data.species,
            status = data.status,
            type = data.type,
            gender = data.gender,
            originLocation = originId,
            lastLocation = lastLocationId,
            imageUrl = data.url,
            episodeIds = episodeIds,
            page = data.page
        )
    }
}