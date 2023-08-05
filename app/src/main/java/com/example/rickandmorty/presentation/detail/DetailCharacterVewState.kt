package com.example.rickandmorty.presentation.detail

data class DetailCharacterVewState(
    val isLoading: Boolean = false,
    val errorText: String = "",
    val inFavorites: Boolean = false,
    val exit: Boolean = false,
)



