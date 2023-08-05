package com.example.rickandmorty.presentation.model.modellocation


import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.presentation.model.modellocation.ExampleModel as ExampleModelPresentation

object Mapper {

    private fun transformToPresentation(model: ExampleEntity): ExampleModelPresentation {
        return ExampleModelPresentation(
            id = model.id,
            name = model.name,
        )
    }

    fun transformToPresentation(task: List<ExampleEntity>): List<ExampleModelPresentation> {
        return task.map { transformToPresentation(it) }
    }

    fun transformToData(model: ExampleModelPresentation): ExampleEntity {
        return ExampleEntity(
            id = model.id,
            name = model.name,
        )
    }

}
