package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


interface NetworkRepository {

    fun getPersons(): Observable<Resource<List<Person>>>

    fun getPersonDetail(id: Int): Observable<Resource<PersonDetail>>
    

}
