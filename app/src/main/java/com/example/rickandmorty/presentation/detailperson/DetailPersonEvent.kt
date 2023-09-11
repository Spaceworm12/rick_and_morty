package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.Person

sealed class DetailPersonEvent {
    class AddToFavorite(val person: Person) : DetailPersonEvent()
    class DeleteFromFavorites(val id: Int) : DetailPersonEvent()
    class ShowPerson(val id: Int) : DetailPersonEvent()
    class CheckStatus(val person: Person) : DetailPersonEvent()
}
