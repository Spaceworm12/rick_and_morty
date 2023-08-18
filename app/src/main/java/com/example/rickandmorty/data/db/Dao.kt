package com.example.rickandmorty.data.db

import androidx.room.*
import androidx.room.Dao
import com.example.rickandmorty.data.db.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao

interface Dao {

    @Query("SELECT * FROM example_table")
    fun getAll(): Observable<List<PersonEntity>>

    @Query("SELECT * FROM example_table")
    fun getAllNotObs(): List<PersonEntity>

    @Query("SELECT * FROM example_table WHERE id = :id")
    fun getInfo(id: Int): Observable<PersonEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM example_table WHERE id = :id)")
    fun exists(id: Int): Observable<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(example: PersonEntity): Single<Long>

    @Query("DELETE FROM example_table WHERE id = :id")
    fun deleteExample(id: Int): Completable

}
