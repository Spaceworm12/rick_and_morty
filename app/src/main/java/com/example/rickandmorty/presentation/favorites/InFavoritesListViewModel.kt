package com.example.rickandmorty.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.model.LocalMapper
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class InFavoritesListViewModel(
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao(),App.getDb())
): ViewModel() {
    private val disposables = CompositeDisposable()
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
    private fun loadLocalPersons() {
        repo.getFavoritePersons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState = viewState.copy(isLoading = true)

                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        viewState=viewState.copy(persons=((resource.data ?: emptyList())))
                    }

                    is Resource.Error -> viewState = viewState.copy(isLoading = true)
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
