package com.example.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.db.ExampleDao
import com.example.rickandmorty.data.db.entity.ExampleEntity


@Database(entities = [ExampleEntity::class], version = 1, exportSchema = true)
abstract class ExampleDataBase: RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}
