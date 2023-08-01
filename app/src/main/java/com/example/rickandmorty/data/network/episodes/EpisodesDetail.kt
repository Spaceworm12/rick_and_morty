package com.example.rickandmorty.data.network.episodes

import com.google.gson.annotations.SerializedName
import ru.lesson.fragmentsample.data.network.model.Stat

data class EpisodesDetail(
    @SerializedName("base_info")
    val baseExperience: Int,
    val height: Int,
    val id: Int,
    @SerializedName("additional_info")
    val locationAreaEncounters: String,
    val name: String,
    val order: Int,
    val stats: List<Stat>,
)

