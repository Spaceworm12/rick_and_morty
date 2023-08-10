package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.Person
import com.example.rickandmorty.presentation.model.modelperson.Person as PersonPresentation


class PersonMapper {

    private fun transformPersonForPresentation(model: Person): PersonPresentation {

        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/character/${number}"
        val avatar = "https://rickandmortyapi.com/api/character/avatar/${number}.jpeg"

        return PersonPresentation(
            name = model.name,
            url = url,
            avatar = avatar,
            id = model.id
        )
    }

    fun transformPersonToPresentation(characters: List<Person>): List<PersonPresentation> {
        return characters.map { transformPersonForPresentation(it) }
    }
}

