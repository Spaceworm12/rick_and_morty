package com.example.rickandmorty.data.network.character

import com.google.gson.annotations.SerializedName

data class  CharacterDetail(
    @SerializedName("base_info")
    val name:String,
    val url: String,
    val avatar: String,
    val status: String,
    val species: String,
    val type: String,
    val gender:String,
    val id: Int,
)

