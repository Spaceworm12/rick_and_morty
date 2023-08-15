package com.example.rickandmorty.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamplePersonModel(
    val name: String?="",
    val url: String?="",
    val avatar: String?="",
    val status: String?="",
    val species: String?="",
    val type: String?="",
    val gender:String?="",
    val id: Int?=null,
    val inFavorites:Boolean=false,
): Parcelable
