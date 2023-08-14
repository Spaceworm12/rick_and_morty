package com.example.rickandmorty.presentation.model


import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.ExampleModel as ExampleModelPresentation

object Mapper {

    private fun transformToPresentation(model: ExampleEntity): ExampleModelPresentation {
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

    fun transformToPresentation(task: List<ExampleEntity>): List<ExampleModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: Person): ExampleEntity {
        return ExampleEntity(
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

}
