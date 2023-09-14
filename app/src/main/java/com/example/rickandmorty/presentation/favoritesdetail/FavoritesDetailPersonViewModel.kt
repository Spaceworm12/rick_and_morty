package com.example.rickandmorty.presentation.favoritesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.favorites.FavoritesListEvents
import com.example.rickandmorty.presentation.model.LocalMapper
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.navigation.Coordinator
import com.example.rickandmorty.util.Resource
import com.github.terrakok.cicerone.Screen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class FavoritesDetailPersonViewModel(
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao())
) : ViewModel() {
    private val coordinator: Coordinator = App.getCoordinator()
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(FavoritesDetailPersonViewState())
    val viewStateObs: LiveData<FavoritesDetailPersonViewState> get() = _viewState
    private var viewState: FavoritesDetailPersonViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: FavoritesDetailPersonEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: FavoritesDetailPersonEvent) {
        when (event) {
            is FavoritesDetailPersonEvent.AddToFavorite -> savePersonToListFavorites(viewState.person)
            is FavoritesDetailPersonEvent.ShowPerson -> loadPersonInfo(event.id)
            is FavoritesDetailPersonEvent.DeletePersonFromFavorite -> deleteFromFavorites(event.id)
            is FavoritesDetailPersonEvent.GoBack -> goBack()
            is FavoritesDetailPersonEvent.GoTo -> goTo(event.screen)
        }
    }
    private fun goTo(screen: Screen){
        coordinator.goTo(screen)
    }
    private fun goBack() = coordinator.goBack()

    private fun loadPersonInfo(id: Int) {
            repo.getFavoritePersonInfo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { resource ->
                    when (resource) {
                        is Resource.Loading -> viewState = viewState.copy(isLoading = false)
                        is Resource.Data -> {
                            viewState.isLoading = false
                            viewState= viewState.copy(person =resource.data, isLoading = false, exit = false)
                        }

                        is Resource.Error -> viewState = viewState.copy(isLoading = false,errorText = resource.error.message?:"error")
                    }

                }
            .addTo(disposables)
    }
    private fun savePersonToListFavorites(person: Person) {
        repo.addPersonToFavorite(LocalMapper.transformToDataDetail(viewState.person))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                viewState = when (result) {
                    is Resource.Loading -> viewState.copy(isLoading = true)
                    is Resource.Data -> viewState.copy(isLoading = false)
                    is Resource.Error -> viewState.copy(errorText = result.error.message?:"error")
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
                    is Resource.Data -> {viewState = viewState.copy(isLoading = false, errorText = "error")
                        FavoritesListEvents.GetFavoritePersons}
                    is Resource.Error -> viewState=viewState.copy(isLoading = false, errorText = resource.error.message?:"error")
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
