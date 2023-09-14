package com.example.rickandmorty.presentation.favoritesdetail

import com.example.rickandmorty.presentation.detailperson.DetailPersonEvent
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.github.terrakok.cicerone.Screen

sealed class FavoritesDetailPersonEvent {
    class AddToFavorite(val person: Person) : FavoritesDetailPersonEvent()
    class ShowPerson(val id: Int) : FavoritesDetailPersonEvent()
    class DeletePersonFromFavorite(val id: Int) : FavoritesDetailPersonEvent()
    class GoTo(val screen: Screen) : FavoritesDetailPersonEvent()
    object GoBack : FavoritesDetailPersonEvent()

}
