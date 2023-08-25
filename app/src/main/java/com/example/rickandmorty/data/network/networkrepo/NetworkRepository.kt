package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.data.network.person.Info
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable

interface NetworkRepository {
    fun getPersons(page:Int): Observable<Resource<List<Person>>>
    fun getInfo(): Observable<Resource<com.example.rickandmorty.presentation.model.modelperson.Info>>
    fun getPersonDetail(id: Int): Observable<Resource<Person>>
}
