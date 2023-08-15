package com.example.rickandmorty.data.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.data.db.Dao
import com.example.rickandmorty.data.db.Db
import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.util.Resource


class LocalRepositoryImplement(private val dao: Dao, private val db: Db) : LocalRepository {
    override fun getFavoritePersons(): Observable<Resource<List<PersonEntity>>> {
        return dao.getAll()
            .map<Resource<List<PersonEntity>>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun addPersonToFavorite(example: PersonEntity): Observable<Resource<Long>> {
        return dao.create(example)
            .toObservable()
            .map<Resource<Long>> { Resource.Data(it) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun deletePersonFromFavorite(id: Long): Observable<Resource<Unit>> {
        return dao.deleteExample(id)
            .andThen(Observable.just(Resource.Success))
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

}
