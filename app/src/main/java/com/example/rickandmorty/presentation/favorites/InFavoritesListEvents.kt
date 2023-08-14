package com.example.rickandmorty.presentation.favorites


sealed class InFavoritesListEvents {
    class LikePerson(val inFavorites: Boolean=false): InFavoritesListEvents()
    class ShowPerson(val id: Int) : InFavoritesListEvents()
}
