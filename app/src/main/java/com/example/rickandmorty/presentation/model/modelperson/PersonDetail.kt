package com.example.rickandmorty.presentation.model.modelperson


data class PersonDetail(
    val name: String?="",
    val url: String?="",
    val avatar: String?="",
    val status: String?="",
    val species: String?="",
    val type: String?="",
    val gender:String?="",
    val id: Int?=null,
)
