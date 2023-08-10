package com.example.rickandmorty.presentation.detailperson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.networkrepo.NetworkRepositoryImpl
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail
import com.example.rickandmorty.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class DetailPersonViewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi())
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
            is DetailPersonEvent.LikePerson -> {}
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
                            viewState.isLoading = true
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
