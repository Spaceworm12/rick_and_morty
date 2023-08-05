package com.example.rickandmorty.data.network

import com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail
import com.example.rickandmorty.presentation.model.modelcharacter.Character
import com.example.rickandmorty.presentation.model.modelcharacter.CharacterMapper
import com.example.rickandmorty.presentation.model.modellocation.Location
import com.example.rickandmorty.presentation.model.modellocation.LocationDetail
import com.example.rickandmorty.presentation.model.modellocation.LocationMapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.util.Resource



class NetworkRepositoryImpl(private val api: RickAndMortyApi): NetworkRepository {

    private val characterMapper = CharacterMapper()
    private val locationMapper = LocationMapper()


        override fun getCharacters(): Observable<Resource<List<Character>>> {
            return api.getCharactersList(20, 20)
                .map { it.results }
                .map<Resource<List<Character>>> {
                    Resource.Data(characterMapper.transformCharacterToPresentation(it))
                }
                .onErrorReturn { Resource.Error(it) }
                .startWith(Resource.Loading)
                .subscribeOn(Schedulers.io())
        }

        override fun getCharacterDetail(model:Character): Observable<Resource<CharacterDetail>> {
            return api.getCharacterInfo(model.id)
                .map<Resource<CharacterDetail>> {
                    Resource.Data(characterMapper.transformCharacterDetailToPresentation(it))
                }
                .onErrorReturn { Resource.Error(it) }
                .startWith(Resource.Loading)
                .subscribeOn(Schedulers.io())
        }


    override fun getLocations(): Observable<Resource<List<Location>>> {
        return api.getLocationsList(10, 10)
            .map{it.results}
            .map<Resource<List<Location>>> {
                Resource.Data(locationMapper.transformLocationToPresentation(it))}
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getLocationsDetail(name: String, type: String): Observable<Resource<LocationDetail>> {
        return api.getLocationsInfo(name, type)
            .map<Resource<LocationDetail>> {
                Resource.Data(locationMapper.transformLocationDetailToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
}
