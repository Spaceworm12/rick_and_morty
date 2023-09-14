package com.example.rickandmorty.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.navigation.Coordinator
import com.example.rickandmorty.util.Resource
import com.github.terrakok.cicerone.Screen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class FavoritesListViewModel(
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao())
) : ViewModel() {
    private val coordinator: Coordinator = App.getCoordinator()
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(FavoritesListViewState())
    val viewStateObs: LiveData<FavoritesListViewState> get() = _viewState
    private var viewState: FavoritesListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: FavoritesListEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: FavoritesListEvents) {
        when (event) {
            is FavoritesListEvents.GetFavoritePersons -> loadLocalPersons()
            is FavoritesListEvents.DeleteFromFavorites -> deleteFromFavorites(event.id)
            is FavoritesListEvents.GoBack -> goBack()
            is FavoritesListEvents.GoTo -> goTo(event.screen)
        }
    }

    init {
        loadLocalPersons()
    }
    private fun goTo(screen: Screen){
        coordinator.goTo(screen)
    }
    private fun goBack() = coordinator.goBack()

    private fun loadLocalPersons() {
        repo.getFavoritePersons()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState = viewState.copy(isLoading = true)

                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        viewState = viewState.copy(persons = ((resource.data)))
                    }

                    is Resource.Error -> viewState = viewState.copy(isLoading = true, errorText = resource.error.message?:"error")
                }
            }
            .addTo(disposables)
    }

    private fun deleteFromFavorites(id: Int) {
        repo.deletePersonFromFavorite(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        FavoritesListEvents.GetFavoritePersons
                    }

                    is Resource.Error -> viewState =
                        viewState.copy(isLoading = false, errorText = resource.error.message?:"error")
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
