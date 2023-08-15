package com.example.rickandmorty.presentation.detailperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.networkrepo.NetworkRepositoryImpl
import com.example.rickandmorty.data.repository.LocalRepository
import com.example.rickandmorty.data.repository.LocalRepositoryImplement
import com.example.rickandmorty.presentation.model.LocalMapper
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class DetailPersonViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi()),
    private val repo: LocalRepository = LocalRepositoryImplement(App.dao(), App.getDb())
) : ViewModel() {
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
            is DetailPersonEvent.AddToFavorite -> {savePersonToListFavorites(viewState.person!!)}
            is DetailPersonEvent.ShowPerson -> {loadPersonInfo(event.id)
            }
        }
    }

    private fun loadPersonInfo(id: Int) {
            networkRepository.getPersonDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { resource ->
                    when (resource) {
                        Resource.Loading -> viewState = viewState.copy(isLoading = true)
                        is Resource.Data -> {viewState=
                            viewState.copy(person =resource.data, isLoading = false, exit = false)
                            viewState.isLoading = false
                        }

                        is Resource.Error -> viewState = viewState.copy(isLoading = false)
                    }

                }
            .addTo(disposables)
    }
    private fun savePersonToListFavorites(person: PersonDetail) {
        repo.addPersonToFavorite(LocalMapper.transformToData(viewState.person!!))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                viewState = when (result) {
                    is Resource.Loading -> viewState.copy(isLoading = true)
                    is Resource.Data -> viewState.copy(isLoading = false)
                    is Resource.Error -> viewState.copy(errorText=(result.error.message ?: ""))
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
