package com.example.rickandmorty.data.mapper.entity_to_domain_model

import com.example.rickandmorty.data.mapper.Mapper
import com.example.rickandmorty.data.models.location.Location
import com.example.rickandmorty.domain.models.location.LocationModel

class LocationEntityToDomainModel :
    Mapper<Location, LocationModel> {

    override fun transform(data: Location): LocationModel {

        return LocationModel(
            id = data.id,
            name = data.name,
            type = data.type,
            dimension = data.dimension,
            residentsIds = data.residentsIds
        )
    }
}