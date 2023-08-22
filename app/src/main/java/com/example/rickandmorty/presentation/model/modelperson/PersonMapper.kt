package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.Person
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.presentation.model.modelperson.Person as PersonPresentation

class PersonMapper(private val localRepo: LocalRepository) {

    private val ids = localRepo.getPersonsIds()
    fun transformPersonForPresentation(model: Person): PersonPresentation {
        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/character/${number}"
        val avatar = "https://rickandmortyapi.com/api/character/avatar/${number}.jpeg"
        val inFavorites = ids.any { it == model.id }
        return PersonPresentation(
            id = model.id,
            name = model.name,
            species = model.species,
            type = model.type,
            avatar = avatar,
            gender = model.gender,
            inFavorites = inFavorites,
            status = model.status,
            url = url
        )
    }

    fun transformPersonToPresentation(characters: List<Person>): List<com.example.rickandmorty.presentation.model.modelperson.Person> {
        return characters.map { transformPersonForPresentation(it) }
    }
}

