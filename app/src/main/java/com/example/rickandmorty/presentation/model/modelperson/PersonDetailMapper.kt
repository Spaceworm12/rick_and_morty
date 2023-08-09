package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.PersonDetail
import com.example.rickandmorty.data.network.person.ResultPersonDetail
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail as PersonDetailPresentation


class PersonDetailMapper {

    fun transformPersonDetailToPresentation(model: PersonDetail): PersonDetailPresentation {
        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/character/${number}"

        return PersonDetailPresentation(
            name = model.name,
            status = model.status,
            species = model.species,
            type = model.type,
            gender = model.gender,
            id = model.id,
            avatar = model.avatar,
            url = url
        )
    }
}
