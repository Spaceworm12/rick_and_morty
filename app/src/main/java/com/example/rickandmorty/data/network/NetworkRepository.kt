package com.example.rickandmorty.data.network

import com.example.rickandmorty.presentation.model.modelcharacter.Character
import com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable


interface NetworkRepository {

    fun getCharacters(): Observable<Resource<List<Character>>>

    fun getCharactersDetail(name: String, avatar:String): Observable<Resource<CharacterDetail>>

}
