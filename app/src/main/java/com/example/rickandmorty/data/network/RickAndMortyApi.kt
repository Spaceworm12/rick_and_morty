package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.network.character.CharacterDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.rickandmorty.data.network.character.Result


interface RickAndMortyApi {
    @GET("character")
    fun getCharactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<Result>

    @GET("character/{name}")
    fun getCharacterInfo(
        @Path("name") name: String,
        @Path("avatar") avatar: String
    ): Observable<CharacterDetail>

}
