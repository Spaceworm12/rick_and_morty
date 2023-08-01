package com.example.rickandmorty.data.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.example.rickandmorty.data.db.entity.ExampleEntity


@Dao
interface ExampleDao {

    @Query("SELECT * FROM example_table")
    fun getAllExamples(): Observable<List<ExampleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExample(example: ExampleEntity): Single<Long>

    @Query("DELETE FROM example_table WHERE id = :id")
    fun deleteExample(id: Long): Completable

}
