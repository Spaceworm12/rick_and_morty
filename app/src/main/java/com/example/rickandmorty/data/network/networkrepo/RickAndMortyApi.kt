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
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Observable<ResultPerson>

    @GET("character/{id}")
    fun getPersonInfo(
        @Path("id") id: Int
    ): Observable<Person>
    @GET("character/page")
    fun getPageInfo(
        @Query("page") limit: Int,
    ): Observable<ResultInfo>
}
