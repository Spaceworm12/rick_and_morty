package com.example.rickandmorty.presentation.favoritesdetail

import com.example.rickandmorty.presentation.model.modelperson.Person


data class FavoritesDetailPersonViewState(
    var isLoading: Boolean = false,
    val exit: Boolean = false,
    val person: Person? = null,
    val errorText:String = ""
)
