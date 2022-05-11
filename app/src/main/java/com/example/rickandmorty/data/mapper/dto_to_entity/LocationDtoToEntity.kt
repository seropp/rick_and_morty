package com.example.rickandmorty.data.mapper.dto_to_entity

import com.example.rickandmorty.data.storage.room.entities.location.LocationEntity
import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.remote.dto.locationDto.LocationDto

class LocationDtoToEntity :
    Mapper<LocationDto, LocationEntity> {

    override fun transform(data: LocationDto): LocationEntity {

        val residentsIds: List<Int> = data.residents.mapNotNull { characterUrl ->
            characterUrl.dropWhile { char ->
                !char.isDigit()
            }.toIntOrNull()
        }

        return LocationEntity(
            id = data.id,
            name = data.name,
            type = data.type,
            dimension = data.dimension,
            residentsIds = residentsIds
        )
    }
}