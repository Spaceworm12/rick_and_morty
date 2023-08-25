package com.example.rickandmorty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "example_table"

@Entity(tableName = TABLE_NAME)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String? = "",
    val url: String? = "",
    val avatar: String? = "",
    val status: String? = "",
    val species: String? = "",
    val type: String? = "",
    val gender: String? = "",
    val inFavorites: Boolean = false,
)
data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String,
)
