package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.presentation.model.modelperson.Person
import io.reactivex.Observable


interface NetworkRepository {

    fun getPersons(): Observable<Resource<List<Person>>>

    fun getPersonDetail(person:Person): Observable<Resource<PersonDetail>>

}
