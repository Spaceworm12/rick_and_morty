package com.example.rickandmorty.data.network.person

import com.google.gson.annotations.SerializedName

data class  PersonDetail(
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

