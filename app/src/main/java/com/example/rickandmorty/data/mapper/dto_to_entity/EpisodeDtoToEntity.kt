package com.example.rickandmorty.data.mapper.dto_to_entity

import com.example.rickandmorty.data.storage.room.entities.episode.EpisodeEntity
import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.remote.dto.episodeDto.EpisodeDto

class EpisodeDtoToEntity :
    Mapper<EpisodeDto, EpisodeEntity> {

    override fun transform(data: EpisodeDto): EpisodeEntity {

        val characterIds: List<Int> = data.characters.mapNotNull { characterUrl ->
            characterUrl.dropWhile { char ->
                !char.isDigit()
            }.toIntOrNull()
        }

        return EpisodeEntity(
            id = data.id,
            name = data.name,
            air_date = data.air_date,
            episode = data.episode,
            residentsIds = characterIds
        )
    }
}