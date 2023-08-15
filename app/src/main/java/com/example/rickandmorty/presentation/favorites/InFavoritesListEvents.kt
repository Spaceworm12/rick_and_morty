package com.example.rickandmorty.presentation.favorites

import com.example.rickandmorty.presentation.model.modelperson.Person


sealed class InFavoritesListEvents {
    object GetFavoritePersons : InFavoritesListEvents()
}
