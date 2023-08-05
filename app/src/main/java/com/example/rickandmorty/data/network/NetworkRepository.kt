package com.example.rickandmorty.data.network

import com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.presentation.model.modelcharacter.Character
import io.reactivex.Observable


interface NetworkRepository {

    fun getCharacters(): Observable<Resource<List<Character>>>

    fun getCharacterDetail(
        name: String,
        avatar: String,
        url: String,
        status: String,
        species: String,
        type: String,
        gender:String,
        id: Int,
    ): Observable<Resource<CharacterDetail>>

    fun getLocations(): Observable<Resource<List<com.example.rickandmorty.presentation.model.modellocation.Location>>>

    fun getLocationsDetail(
        name: String,
        type: String
    ): Observable<Resource<com.example.rickandmorty.presentation.model.modellocation.LocationDetail>>

}
