package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.util.Resource


interface ItemRepository {
    fun getItems(): Observable<Resource<List<ExampleEntity>>>

    fun insertExample(example: ExampleEntity): Observable<Resource<Int>>

    fun deleteExample(id: Int): Observable<Resource<Unit>>
}
