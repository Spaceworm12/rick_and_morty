package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.data.db.ExampleDao
import com.example.rickandmorty.data.db.entity.ExampleEntity
import com.example.rickandmorty.util.Resource


class ItemRepositoryImpl(private val exampleDao: ExampleDao) : ItemRepository {
    override fun getItems(): Observable<Resource<List<ExampleEntity>>> {
        return exampleDao.getAllExamples()
            .map<Resource<List<ExampleEntity>>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun insertExample(example: ExampleEntity): Observable<Resource<Long>> {
        return exampleDao.insertExample(example)
            .toObservable()
            .map<Resource<Long>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun deleteExample(id: Long): Observable<Resource<Unit>> {
        return exampleDao.deleteExample(id)
            .andThen(Observable.just(Resource.Success))
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

}
