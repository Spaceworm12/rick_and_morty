package com.example.rickandmorty.presentation.listperson

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


class PersonListViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi())
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(PersonListViewState())
    val viewStateObs: LiveData<PersonListViewState> get() = _viewState
    private var viewState: PersonListViewState
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
                    Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        viewState = viewState.copy(persons = (resource.data ?: emptyList()))
                    }

                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
