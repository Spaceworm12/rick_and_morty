package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.data.db.ExampleDao
import com.example.rickandmorty.data.db.ExampleDataBase
import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.util.Resource


class LocalRepositoryImplement(private val exampleDao: ExampleDao) : LocalRepository {
    override fun getFavoritePersons(): Observable<Resource<List<ExampleEntity>>> {
        return exampleDao.getAllExamples()
            .map<Resource<List<ExampleEntity>>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun addPersonToFavorite(example: ExampleEntity): Observable<Resource<Int>> {
        return exampleDao.insertExample(example)
            .toObservable()
            .map<Resource<Int>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun deletePersonFromFavorite(id: Int): Observable<Resource<Unit>> {
        return exampleDao.deleteExample(id)
            .andThen(Observable.just(Resource.Success))
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

}
