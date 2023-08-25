package com.example.rickandmorty.data.network.networkrepo

import com.example.rickandmorty.data.network.person.Person
import com.example.rickandmorty.data.network.person.ResultInfo
import com.example.rickandmorty.data.network.person.ResultPerson
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyApi {
    @GET("character")
    fun getCharactersList(
    ): Observable<ResultPerson>

    @GET("character/{id}")
    fun getPersonInfo(
        @Path("id") id: Int
    ): Observable<Person>

    @GET("character/")
    fun getInfo(
//        @Query("count") count: Int,
        @Query("page") pages: Int,
//        @Query("next") next: Int,
//        @Query("prev") prev: Int,
    ): Observable<ResultInfo>
}
