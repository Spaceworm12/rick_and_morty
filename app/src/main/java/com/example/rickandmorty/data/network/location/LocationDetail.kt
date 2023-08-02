package com.example.rickandmorty.data.network.location

import com.example.rickandmorty.data.network.character.Character
import com.google.gson.annotations.SerializedName

data class LocationDetail(
    @SerializedName("base_info")
    val type: String,
    val dimension: String,
    val id: Int,
    @SerializedName("additional_info")
    val residents: List<Character>,
)
