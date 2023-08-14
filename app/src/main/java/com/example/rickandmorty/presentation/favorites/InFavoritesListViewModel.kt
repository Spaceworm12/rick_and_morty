package com.example.rickandmorty.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.model.Mapper
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class InFavoritesListViewModel(
    private val repo: LocalRepository = LocalRepositoryImplement(App.getExampleDao())
): ViewModel() {
    private val disposables = CompositeDisposable()
    val persons = MutableLiveData<List<Person>>(emptyList())
    val person = MutableLiveData<Person>()
    private val _viewState = MutableLiveData(InFavoritesListViewState())
    val viewStateObs: LiveData<InFavoritesListViewState> get() = _viewState
    private var viewState: InFavoritesListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        loadLocalPersons()
    }
    private fun savePersonToListFavorites(id: Long) {
        repo.addPersonToFavorite(Mapper.transformToData(person.value!!.copy(id=id)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    Resource.Loading -> {}
                    is Resource.Data -> exit.postValue(true)
                    is Resource.Error -> errorText.postValue(result.error.message ?: "")
                }
            }
            .addTo(disposables)
    }


    private fun loadLocalPersons() {
        repo.getFavoritePersons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState.isLoading = true

                    is Resource.Data -> {
                        persons.postValue((resource.data ?: emptyList()) as List<Person>?)
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
