package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.util.Resource


interface LocalRepository {
    fun getFavoritePersons(): Observable<Resource<List<ExampleEntity>>>

    fun addPersonToFavorite(example: ExampleEntity): Observable<Resource<Int>>

    fun deletePersonFromFavorite(id: Int): Observable<Resource<Unit>>
}
