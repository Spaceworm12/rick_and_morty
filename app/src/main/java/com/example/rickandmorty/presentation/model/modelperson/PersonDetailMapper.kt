package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.PersonDetail
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail as CharacterDetailPresentation


class PersonDetailMapper {

    fun transformPersonDetailToPresentation(model: PersonDetail): CharacterDetailPresentation {
        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val ids = "https://rickandmortyapi.com/api/character/${number}"

        return CharacterDetailPresentation(
            name = model.name,
            status = model.status,
            species = model.species,
            type = model.type,
            gender = model.gender,
            id = model.id,
            avatar = model.avatar,
            url = ids
        )
    }
}

