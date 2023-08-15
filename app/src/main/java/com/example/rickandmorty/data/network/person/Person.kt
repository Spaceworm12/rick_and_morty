package com.example.rickandmorty.data.network.person


data class Person(
    val name:String,
    val url: String,
    val avatar: String,
    val status: String,
    val species: String,
    val type: String,
    val gender:String,
    val id: Int,
    val inFavorites:Boolean=false,
)
