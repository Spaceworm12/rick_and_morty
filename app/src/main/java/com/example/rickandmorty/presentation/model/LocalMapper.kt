package com.example.rickandmorty.presentation.model


import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.presentation.model.ExamplePersonModel as ExampleModelPresentation

object LocalMapper {

    private fun transformToPresentation(model: PersonEntity): ExampleModelPresentation {
        return ExampleModelPresentation(
            id = model.id,
            name = model.name,
            species = model.species,
            type = model.type,
            avatar = model.avatar,
            gender = model.gender,
            inFavorites = model.inFavorites,
            status = model.status,
            url = model.url
        )
    }

    fun transformToPresentation(task: List<PersonEntity>): List<ExampleModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: PersonDetail): PersonEntity {
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
