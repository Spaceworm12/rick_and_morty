package com.example.rickandmorty.data.db

import androidx.room.*
import androidx.room.Dao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.example.rickandmorty.data.db.entity.PersonEntity


@Dao

interface Dao {

    @Query("SELECT * FROM example_table")
    fun getAll(): Observable<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(example: PersonEntity): Single<Long>

    @Query("DELETE FROM example_table WHERE id = :id")
    fun deleteExample(id: Long): Completable

}
