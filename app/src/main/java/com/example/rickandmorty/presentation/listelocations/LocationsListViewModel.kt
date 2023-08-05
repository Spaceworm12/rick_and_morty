package com.example.rickandmorty.presentation.listelocations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.NetworkRepositoryImpl
import com.example.rickandmorty.presentation.model.modellocation.Location
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class LocationsListViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi())
): ViewModel() {

    private val disposables = CompositeDisposable()

    val locations = MutableLiveData<List<Location>>(emptyList())
    val loading = MutableLiveData(false)

    init {
        loadLocations()
    }

    private fun loadLocations() {
        networkRepository.getLocations()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> loading.postValue(true)

                    is Resource.Data -> {
                        locations.postValue(resource.data ?: emptyList())
                        loading.postValue(false)
                    }

                    is Resource.Error -> loading.postValue(false)
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
