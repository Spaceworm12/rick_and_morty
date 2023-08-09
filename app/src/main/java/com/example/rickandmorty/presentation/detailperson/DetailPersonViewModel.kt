package com.example.rickandmorty.presentation.detailperson

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
    var person = MutableLiveData<PersonDetail>()
    var loading = MutableLiveData(false)
    var exit = MutableLiveData(false)

    init {
        person.value?.let { loadPersonInfo(person.value!!.id) }
    }

    fun submitUIEvent(event: DetailPersonEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailPersonEvent) {
        when (event) {
            is DetailPersonEvent.LikePerson -> {}
            is DetailPersonEvent.OpenPerson -> {
                person.value?.let { loadPersonInfo(it.id) }
            }
        }
    }

    private fun loadPersonInfo(id: Int) {
      let {
            networkRepository.getPersonDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { resource ->
                    when (resource) {
                        Resource.Loading -> loading.postValue(true)
                        is Resource.Data -> {
                            person.postValue(resource.data as PersonDetail)
                            loading.postValue(false)
                        }

                        is Resource.Error -> loading.postValue(false)
                    }
                }
        }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

}
