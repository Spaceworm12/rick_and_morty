package com.example.rickandmorty.data.network.character

import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("base_info")
    val status: String,
    val species: String,
    val type: String,
    val gender:String,
    val id: Int,
    @SerializedName("additional_info")
    val location: Any,
//    val episode: List<Any>,
    val origin: Any,
)

