package com.example.rickandmorty.presentation.model.modelperson

import com.example.rickandmorty.data.network.person.Person
import com.example.rickandmorty.data.network.person.PersonDetail
import com.example.rickandmorty.presentation.model.modelperson.Person as CharacterPresentation
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail as CharacterDetailPresentation


class PersonMapper {

    private fun transformCharacterForPresentation(model: Person): CharacterPresentation {

        val number = if (model.url.endsWith("/")) {
            model.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            model.url.takeLastWhile { it.isDigit() }
        }
        val url = "https://rickandmortyapi.com/api/character/${number}"
        val avatar = "https://rickandmortyapi.com/api/character/avatar/${number}.jpeg"
        val ids = "https://rickandmortyapi.com/api/character/${number}"

        return CharacterPresentation(
            name = model.name,
            url = url,
            avatar = avatar,
        )
    }

    fun transformCharacterToPresentation(characters: List<Person>): List<CharacterPresentation> {
        return characters.map { transformCharacterForPresentation(it) }
    }
}

