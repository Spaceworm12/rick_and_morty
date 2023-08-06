package com.example.rickandmorty.presentation.detailperson


sealed class DetailPersonEvent {
    class SetCharacter(val id: Int): DetailPersonEvent()
    class LikeCharacter(val inFavorites: Boolean=false): DetailPersonEvent()
}
