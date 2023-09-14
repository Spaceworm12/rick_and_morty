package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.db.Dao
import com.example.rickandmorty.data.db.Db
import com.example.rickandmorty.data.db.entity.PersonEntity
import com.example.rickandmorty.presentation.model.LocalMapper
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

// private val db: Db - неиспользуется
class LocalRepositoryImplement(private val dao: Dao, private val db: Db) : LocalRepository {
    override fun getPersonsIds(): List<Int> {
        return dao.getAllNotObs().map { it.id }
    }

    override fun getFavoritePersons(): Observable<Resource<List<Person>>> {
        return dao.getAll()
            .map<Resource<List<Person>>> { Resource.Data(LocalMapper.transformToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getFavoritePersonInfo(id: Int): Observable<Resource<Person>> {
        return dao.getInfo(id)
            .map<Resource<Person>> { Resource.Data(LocalMapper.transformToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getStatusPerson(id: Int): Observable<Resource<Boolean>> {
        return dao.exists(id)
            .map<Resource<Boolean>> { Resource.Data(it) }
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

    override fun deletePersonFromFavorite(id: Int): Observable<Resource<Unit>> {
        return dao.deletePerson(id)
            .andThen(Observable.just(Resource.Success))
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
}
