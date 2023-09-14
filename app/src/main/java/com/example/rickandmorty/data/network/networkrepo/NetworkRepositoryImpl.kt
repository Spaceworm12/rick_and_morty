package com.example.rickandmorty.data.network.networkrepo


import com.example.rickandmorty.application.App
import com.example.rickandmorty.presentation.model.modelperson.Info
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonMapper
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NetworkRepositoryImpl(private val api: RickAndMortyApi) : NetworkRepository {

    private val personMapper = PersonMapper(LocalRepositoryImplement(App.dao()))
    override fun getPersons(page:Int): Observable<Resource<List<Person>>> {
        return api.getCharactersList(page)
            .map { it.results }
            .map<Resource<List<Person>>> {
                Resource.Data(personMapper.transformPersonToPresentation(it))
            }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
    override fun getInfo(): Observable<Resource<Info>> {
        return api.getInfo()
            .map { it.result }
            .map<Resource<Info>> {
                Resource.Data(personMapper.transformInfoForPresentation(it))
            }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
    override fun getPersonDetail(id: Int): Observable<Resource<Person>> {
        return api.getPersonInfo(id)
            .map<Resource<Person>> {
                Resource.Data(personMapper.transformPersonForPresentation(it))
            }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

}
