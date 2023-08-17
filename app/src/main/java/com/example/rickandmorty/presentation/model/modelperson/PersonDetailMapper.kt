package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.PersonDetail
import com.example.rickandmorty.presentation.model.modelperson.Person as PersonDetailPresentation


class PersonDetailMapper {

    fun transformPersonDetailForPresentation(model: PersonDetail): PersonDetailPresentation {
        val number = if (model.url!!.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/character/${number}/"
        val avatar = "https://rickandmortyapi.com/api/character/avatar/${number}.jpeg"


        return PersonDetailPresentation(
            name = model.name?:"",
            status = model.status?:"",
            species = model.species?:"",
            type = model.type?:"",
            gender = model.gender?:"",
            id = model.id,
            avatar = avatar,
            url = url,
        )
    }

}
