package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable


interface NetworkRepository {

    fun getPersons(): Observable<Resource<List<Person>>>

    fun getPersonDetail(id: Int): Observable<Resource<PersonDetail>>

}
