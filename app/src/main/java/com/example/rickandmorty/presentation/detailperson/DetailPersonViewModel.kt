package com.example.rickandmorty.presentation.detailperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.networkrepo.NetworkRepositoryImpl
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.favorites.FavoritesListEvents
import com.example.rickandmorty.presentation.model.LocalMapper
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.util.Resource
import com.github.terrakok.cicerone.Screen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class DetailPersonViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi()),
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao())
) : ViewModel() {
    private val coordinator = App.getCoordinator()
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(DetailPersonViewState())
    val viewStateObs: LiveData<DetailPersonViewState> get() = _viewState
    private var viewState: DetailPersonViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: DetailPersonEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailPersonEvent) {
        when (event) {
            is DetailPersonEvent.AddToFavorite -> savePersonToListFavorites(viewState.person)
            is DetailPersonEvent.ShowPerson -> loadPersonInfo(event.id)
            is DetailPersonEvent.DeleteFromFavorites -> deleteFromFavorites(event.id)
            is DetailPersonEvent.CheckStatus -> checkStatusPerson(event.person)
            is DetailPersonEvent.GoBack -> goBack()
            is DetailPersonEvent.GoTo -> goTo(event.screen)
        }
    }
    private fun goTo(screen:Screen){
        coordinator.goTo(screen)
    }
    private fun goBack() = coordinator.goBack()

    private fun loadPersonInfo(id: Int) {
        networkRepository.getPersonDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> {viewState.isLoading = true}
                    is Resource.Data -> {
                        viewState.isLoading = false
                        viewState =
                            viewState.copy(person = resource.data, isLoading = false, exit = false)
                    }

                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
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
                    is Resource.Error -> viewState.copy(errorText = (result.error.message ?: ""))
                }
            }
            .addTo(disposables)
    }

    private fun checkStatusPerson(person: Person) {
        repo.getStatusPerson(person.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> {}
                    is Resource.Data -> {
                        person.inFavorites = resource.data
                    }

                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
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
                        viewState.copy(isLoading = false, errorText = "error")
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
