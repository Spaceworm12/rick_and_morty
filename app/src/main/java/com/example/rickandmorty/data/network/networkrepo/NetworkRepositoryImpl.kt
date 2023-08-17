package com.example.rickandmorty.data.network.networkrepo


import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonMapper
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor


class NetworkRepositoryImpl(private val api: RickAndMortyApi) : NetworkRepository {

    private val personMapper = PersonMapper(LocalRepositoryImplement(App.dao(), App.getDb()))
    val interceptor = HttpLoggingInterceptor()


    override fun getPersons(): Observable<Resource<List<Person>>> {
        return api.getCharactersList(20, 20)
            .map { it.results }
            .map<Resource<List<Person>>> {
                Resource.Data(personMapper.transformPersonToPresentation(it))
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
