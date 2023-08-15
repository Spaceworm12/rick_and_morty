package com.example.rickandmorty.presentation.model


import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail

object LocalMapperDetail {

    private fun transformToPresentation(model: PersonEntity): Person {
        return Person(
            name = model.name!!, url = model.url!!, avatar = model.avatar!!, id = model.id, inFavorites = true
        )
    }

    fun transformToPresentation(task: List<PersonEntity>): List<Person> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: PersonDetail): PersonEntity {
        return PersonEntity(
            id = model.id,
            name = model.name,
            species = model.species,
            type = model.type,
            avatar = model.avatar,
            gender = model.gender,
            inFavorites = true,
            status = model.status,
            url = model.url
        )
    }

}
