package com.example.rickandmorty.data.db

import androidx.room.*
import androidx.room.Dao
import com.example.rickandmorty.data.db.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao

interface Dao {

    @Query("SELECT * FROM persons_table")
    fun getAll(): Observable<List<PersonEntity>>

    @Query("SELECT * FROM persons_table")
    fun getAllNotObs(): List<PersonEntity>

    @Query("SELECT * FROM persons_table WHERE id = :id")
    fun getInfo(id: Int): Observable<PersonEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM persons_table WHERE id = :id)")
    fun exists(id: Int): Observable<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(example: PersonEntity): Single<Long>

    @Query("DELETE FROM persons_table WHERE id = :id")
    fun deletePerson(id: Int): Completable

}
