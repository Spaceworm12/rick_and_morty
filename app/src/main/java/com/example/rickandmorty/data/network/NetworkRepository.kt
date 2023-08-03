package com.example.rickandmorty.data.network

import com.example.rickandmorty.util.Resource
import io.reactivex.Observable


interface NetworkRepository {

    fun getCharacters(): Observable<Resource<List<com.example.rickandmorty.presentation.model.Character>>>

    fun getCharactersDetail(name: String): Observable<Resource<com.example.rickandmorty.presentation.model.CharacterDetail>>

}
