package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.network.character.Character
import com.example.rickandmorty.data.network.character.CharacterDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable


interface NetworkRepository {

    fun getCharacters(): Observable<Resource<List<Character>>>

    fun getCharactersDetail(name: String): Observable<Resource<CharacterDetail>>

}
