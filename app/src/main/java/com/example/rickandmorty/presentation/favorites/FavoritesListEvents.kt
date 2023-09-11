package com.example.rickandmorty.presentation.favorites

sealed class FavoritesListEvents {
    object GetFavoritePersons : FavoritesListEvents()
    class DeleteFromFavorites(val id: Int) : FavoritesListEvents()
}
