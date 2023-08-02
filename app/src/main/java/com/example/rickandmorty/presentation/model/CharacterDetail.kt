package com.example.rickandmorty.presentation.model


data class CharacterDetail(
    val status: String,
    val species: String,
    val type: String,
    val gender:String,
    val id: Int,
    val location: Any,
//    val episode: List<Character>,
    val origin: Any,
)
