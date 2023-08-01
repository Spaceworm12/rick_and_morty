package ru.lesson.fragmentsample.data.repository

import io.reactivex.Observable
import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.util.Resource


interface ItemRepository {
    fun getItems(): Observable<Resource<List<ExampleEntity>>>

    fun insertExample(example: ExampleEntity): Observable<Resource<Long>>

    fun deleteExample(id: Long): Observable<Resource<Unit>>
}
