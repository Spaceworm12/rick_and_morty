package com.example.rickandmorty.presentation.model.modelcharacter

import com.example.rickandmorty.data.network.character.Character
import com.example.rickandmorty.data.network.character.CharacterDetail
import com.example.rickandmorty.presentation.model.modelcharacter.Character as CharacterPresentation
import com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail as CharacterDetailPresentation


class CharacterMapper {

    private fun transformCharacterForPresentation(model: Character): CharacterPresentation {

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
            id = ids
        )
    }

    fun transformCharacterToPresentation(characters: List<Character>): List<CharacterPresentation> {
        return characters.map { transformCharacterForPresentation(it) }
    }

    fun transformCharacterDetailToPresentation(model: CharacterDetail): CharacterDetailPresentation {
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

