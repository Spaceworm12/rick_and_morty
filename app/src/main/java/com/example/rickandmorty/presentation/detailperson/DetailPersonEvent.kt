package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.PersonDetail


sealed class DetailPersonEvent {
    class AddToFavorite(val person: PersonDetail): DetailPersonEvent()
    class ShowPerson(val id: Int) : DetailPersonEvent()
}
