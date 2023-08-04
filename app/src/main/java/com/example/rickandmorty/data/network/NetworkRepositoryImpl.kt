package com.example.rickandmorty.data.network


import com.example.rickandmorty.presentation.model.modelcharacter.Character
import com.example.rickandmorty.presentation.model.modelcharacter.CharacterDetail
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.presentation.model.modelcharacter.CharacterMapper


class NetworkRepositoryImpl(private val api: RickAndMortyApi): NetworkRepository {

    private val characterMapper = CharacterMapper()

    override fun getCharacters(): Observable<Resource<List<Character>>> {
        return api.getCharactersList(20, 20)
            .map { it.results }
            .map<Resource<List<Character>>> { Resource.Data(characterMapper.transformToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getCharactersDetail(name: String,avatar:String): Observable<Resource<CharacterDetail>> {
        return api.getCharacterInfo(name,avatar)
            .map<Resource<CharacterDetail>> { Resource.Data(characterMapper.transformCharacterDetailToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
}
