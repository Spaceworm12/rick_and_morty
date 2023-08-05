package com.example.rickandmorty.presentation.detail


sealed class DetailCharacterEvent {
    class SetCharacter(val id: Int): DetailCharacterEvent()
    class LikeCharacter(val inFavorites: Boolean=false): DetailCharacterEvent()
}
