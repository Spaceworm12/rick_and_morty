package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.network.NetworkRepository
import com.example.rickandmorty.data.network.RickAndMortyApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.example.rickandmorty.util.Resource


class NetworkRepositoryImpl(private val api: RickAndMortyApi): NetworkRepository {

    private val pokemonMapper = PokemonMapper()

    override fun getPokemons(): Observable<Resource<List<Pokemon>>> {
        return api.getPokemonList(50, 50)
            .map { it.results }
            .map<Resource<List<Pokemon>>> { Resource.Data(pokemonMapper.transformToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }

    override fun getPokemonDetails(name: String): Observable<Resource<PokemonDetail>> {
        return api.getPokemonInfo(name)
            .map<Resource<PokemonDetail>> { Resource.Data(pokemonMapper.transformPokemonDetailToPresentation(it)) }
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
}
