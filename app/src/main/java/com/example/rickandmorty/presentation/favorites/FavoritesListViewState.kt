package com.example.rickandmorty.presentation.favorites

import com.example.rickandmorty.presentation.model.modelperson.Person


data class FavoritesListViewState(
    val persons:List<Person> = emptyList(),
    val errorText:String = "",
    val person:Person?=null,
    var isLoading: Boolean = false,
    val exit: Boolean = false,
){
}


