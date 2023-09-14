package com.example.rickandmorty.presentation.listperson

import com.example.rickandmorty.presentation.model.modelperson.Person
import com.github.terrakok.cicerone.Screen

sealed class PersonListEvents {
    class AddToFavorite(val person: Person) : PersonListEvents()
    class DeleteFromFavorites(val id: Int) : PersonListEvents()
    class CheckStatus(val person: Person) : PersonListEvents()
    class ToNextPage(val page: Int) : PersonListEvents()
    class GoTo(val screen: Screen) : PersonListEvents()
    object GoBack : PersonListEvents()
}
