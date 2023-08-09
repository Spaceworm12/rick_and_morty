package com.example.rickandmorty.presentation.detailperson


sealed class DetailPersonEvent {
    class LikePerson(val inFavorites: Boolean=false): DetailPersonEvent()
    class ShowPerson(val id: Int) : DetailPersonEvent()
}
