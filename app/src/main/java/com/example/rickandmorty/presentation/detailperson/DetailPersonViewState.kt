package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.Person


data class DetailPersonViewState(
    var isLoading: Boolean = false,
    val exit: Boolean = false,
    val person: Person = Person(id=0),
    val errorText:String = ""
)
