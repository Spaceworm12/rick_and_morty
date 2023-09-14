package com.example.rickandmorty.presentation.listperson

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class PersonListViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi()),
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(PersonListViewState())
    val viewStateObs: LiveData<PersonListViewState> get() = _viewState
    private var viewState: PersonListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    fun submitUIEvent(event: PersonListEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: PersonListEvents) {
        when (event) {
            is PersonListEvents.AddToFavorite -> savePersonToListFavorites(event.person)
            is PersonListEvents.DeleteFromFavorites -> deleteFromFavorites(event.id)
            is PersonListEvents.CheckStatus -> checkStatusPerson(event.person)
            is PersonListEvents.ToNextPage -> loadPersons(event.page)
        }
    }

    init {
        loadPersons(viewState.currentPage)
        loadInfo()
    }

    private fun loadPersons(page:Int) {
        networkRepository.getPersons(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        viewState = viewState.copy(persons = (resource.data))
                    }
                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
                }
            }
            .addTo(disposables)
    }
    private fun loadInfo() {
        networkRepository.getInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> {
                        viewState = viewState.copy(isLoading = false)
                        viewState = viewState.copy(pageInfo = (resource.data))
                    }
                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
                }
            }
            .addTo(disposables)
    }
    private fun checkStatusPerson(person:Person) {
        repo.getStatusPerson(person.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> {}
                    is Resource.Data -> {
                        person.inFavorites=resource.data
                    }
                    is Resource.Error -> viewState = viewState.copy(isLoading = false)
                }
            }
            .addTo(disposables)
    }

    private fun savePersonToListFavorites(person: Person) {
        repo.addPersonToFavorite(LocalMapper.transformToDataDetail(person))
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
