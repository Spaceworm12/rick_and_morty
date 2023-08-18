package com.example.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.db.entity.PersonEntity


@Database(entities = [PersonEntity::class], version = 1, exportSchema = false)
abstract class Db : RoomDatabase() {
    abstract fun exampleDao(): Dao
}
