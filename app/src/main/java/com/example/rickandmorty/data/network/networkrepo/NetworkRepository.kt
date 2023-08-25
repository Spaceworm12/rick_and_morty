package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.data.network.person.Info
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable

interface NetworkRepository {

    fun getPersons(): Observable<Resource<List<Person>>>
    fun getInfo(page:Int): Observable<Resource<com.example.rickandmorty.presentation.model.modelperson.Info>>
//    fun toPage(page:Int): Observable<Resource<com.example.rickandmorty.presentation.model.modelperson.Info>>

    fun getPersonDetail(id: Int): Observable<Resource<Person>>


}
