package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.Person


sealed class DetailPersonEvent {
    class LikePerson(val inFavorites: Boolean=false): DetailPersonEvent()
    class OpenPerson(val person: Person) : DetailPersonEvent()
}
