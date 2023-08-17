package com.example.rickandmorty.presentation.model


import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.presentation.model.modelperson.Person

object LocalMapper {

    fun transformToPresentation(model: PersonEntity): Person {
        return Person(
            name = model.name!!, url = model.url!!, avatar = model.avatar!!, id = model.id, inFavorites = model.inFavorites,
            species = model.species, type =  model.type, gender =  model.gender, status =  model.status
        )
    }

    fun transformToPresentation(task: List<PersonEntity>): List<Person> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToDataDetail(model: Person): PersonEntity {
        return PersonEntity(
            id = model.id!!,
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
    fun transformToData(model: Person): PersonEntity {
        return PersonEntity(
            id = model.id!!,
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
