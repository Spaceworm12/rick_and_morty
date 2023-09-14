package com.example.rickandmorty.presentation.favorites

import com.example.rickandmorty.presentation.detailperson.DetailPersonEvent
import com.github.terrakok.cicerone.Screen

sealed class FavoritesListEvents {
    object GetFavoritePersons : FavoritesListEvents()
    class DeleteFromFavorites(val id: Int) : FavoritesListEvents()
    class GoTo(val screen: Screen) : FavoritesListEvents()
    object GoBack : FavoritesListEvents()
}
