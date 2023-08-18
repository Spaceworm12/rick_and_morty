package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource


interface LocalRepository {
    fun getPersonsIds(): List<Int>
    fun getFavoritePersons(): Observable<Resource<List<Person>>>
    fun getFavoritePersonInfo(id: Int): Observable<Resource<Person>>
    fun getStatusPerson(id:Int): Observable<Resource<Boolean>>
    fun addPersonToFavorite(example: PersonEntity): Observable<Resource<Long>>
    fun deletePersonFromFavorite(id: Int): Observable<Resource<Unit>>
}
