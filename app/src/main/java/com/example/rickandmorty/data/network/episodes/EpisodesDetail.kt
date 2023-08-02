package com.example.rickandmorty.data.network.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesDetail(
    @SerializedName("base_info")
    val id: Int,
    @SerializedName("additional_info")
    val episode: String,
)

