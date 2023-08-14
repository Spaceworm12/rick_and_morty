package com.example.rickandmorty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "list_of_favorites_persons"

@Entity(tableName = TABLE_NAME)
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
)
