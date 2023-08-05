package com.example.rickandmorty.presentation.model.modellocation

import com.example.rickandmorty.data.network.location.Location
import com.example.rickandmorty.data.network.location.LocationDetail
import com.example.rickandmorty.presentation.model.modellocation.Location as LocationPresentation
import com.example.rickandmorty.presentation.model.modellocation.LocationDetail as LocationDetailPresentation


class LocationMapper {

    private fun transformLocationForPresentation(model: Location): LocationPresentation {

        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/location/${number}"

        return LocationPresentation(
            name = model.name,
            url = url,
            type = model.type
        )
    }

    fun transformLocationToPresentation(locations: List<Location>): List<LocationPresentation> {
        return locations.map { transformLocationForPresentation(it) }
    }

    fun transformLocationDetailToPresentation(model: LocationDetail): LocationDetailPresentation {
        return LocationDetailPresentation(
            id = model.id,
            dimension = model.dimension,
            created = model.created,
            type = model.type
        )
    }

}
