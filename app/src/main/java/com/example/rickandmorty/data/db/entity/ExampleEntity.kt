package com.example.rickandmorty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "example_table"

@Entity(tableName = TABLE_NAME)
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true)
    val name: String?="",
    val url: String?="",
    val avatar: String?="",
    val status: String?="",
    val species: String?="",
    val type: String?="",
    val gender:String?="",
    val id: Int?=null,
    val inFavorites:Boolean=false,
)
