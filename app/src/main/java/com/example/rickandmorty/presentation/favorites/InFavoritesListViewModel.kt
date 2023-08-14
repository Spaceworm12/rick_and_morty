package com.example.rickandmorty.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.networkrepo.NetworkRepositoryImpl
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class InFavoritesListViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi())
): ViewModel() {
    private val disposables = CompositeDisposable()
    val persons = MutableLiveData<List<Person>>(emptyList())
    private val _viewState = MutableLiveData(InFavoritesListViewState())
    val viewStateObs: LiveData<InFavoritesListViewState> get() = _viewState
    private var viewState: InFavoritesListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        loadPersons()
    }

    private fun loadPersons() {
        networkRepository.getPersons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState.isLoading = true

                    is Resource.Data -> {
                        persons.postValue(resource.data ?: emptyList())
                        viewState.isLoading = false
                    }

                    is Resource.Error -> viewState.isLoading = false
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
