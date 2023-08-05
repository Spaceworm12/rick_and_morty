package com.example.rickandmorty.presentation.model.modellocation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleModel(
    val id: Long,
    var name: String,
): Parcelable
