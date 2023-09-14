package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.Person
import com.github.terrakok.cicerone.Screen

sealed class DetailPersonEvent {
    class AddToFavorite(val person: Person) : DetailPersonEvent()
    class DeleteFromFavorites(val id: Int) : DetailPersonEvent()
    class ShowPerson(val id: Int) : DetailPersonEvent()
    class CheckStatus(val person: Person) : DetailPersonEvent()
    class GoTo(val screen: Screen) : DetailPersonEvent()
    object GoBack : DetailPersonEvent()
}
