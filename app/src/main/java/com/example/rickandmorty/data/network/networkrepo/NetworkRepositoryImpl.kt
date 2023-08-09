package com.example.rickandmorty.data.network.networkrepo


import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.presentation.model.modelperson.PersonDetailMapper
import com.example.rickandmorty.presentation.model.modelperson.PersonMapper
import com.example.rickandmorty.util.Resource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class NetworkRepositoryImpl(private val api: RickAndMortyApi) : NetworkRepository {

    private val personMapper = PersonMapper()
    private val personDetailMapper = PersonDetailMapper()


    override fun getPersons(): Observable<Resource<List<Person>>> {
        return api.getCharactersList(20, 20)
            .map { it.results }
            .map<Resource<List<Person>>> {
                Resource.Data(personMapper.transformCharacterToPresentation(it))
            }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getPersonDetail(id: Int): Observable<Resource<PersonDetail>> {
        return api.getPersonInfo(id)
            .map { it.results }
            .map<Resource<PersonDetail>> {
                Resource.Data(personDetailMapper.transformPersonDetailToPresentation(it))
            }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

}
