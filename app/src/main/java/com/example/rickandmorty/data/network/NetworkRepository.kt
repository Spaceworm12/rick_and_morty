package com.example.rickandmorty.data.network

import com.example.rickandmorty.util.Resource
import io.reactivex.Observable


interface NetworkRepository {

    fun getCharacters(): Observable<Resource<List<com.example.rickandmorty.presentation.model.modelcharacter.Character>>>

    fun getCharactersDetail(
        name: String,
        avatar: String
    ): Observable<Resource<com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail>>

    fun getLocations(): Observable<Resource<List<com.example.rickandmorty.presentation.model.modellocation.Location>>>

    fun getLocationsDetail(
        name: String,
        type: String
    ): Observable<Resource<com.example.rickandmorty.presentation.model.modellocation.LocationDetail>>

}
