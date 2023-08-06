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
    val person = MutableLiveData<PersonDetail>()
    val loading = MutableLiveData(false)

    init {
        loadPersonInfo()
    }

    fun submitUIEvent(event: DetailPersonEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailPersonEvent) {
        when (event) {
            is DetailPersonEvent.SetCharacter -> {}
            is DetailPersonEvent.LikeCharacter -> {}
        }
    }

    private fun loadPersonInfo() {
        networkRepository.getPersonDetail(person.value)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> loading.postValue(true)

                    is Resource.Data -> {
                        person.postValue((resource.data ?:"") as PersonDetail?)
                        loading.postValue(false)
                    }

                    is Resource.Error -> loading.postValue(false)
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
    }

//    private fun likeCharacter(id: Long) {
//
//        itemRepository.insertExample(Mapper.transformToData( exampleCharacter.value!!.copy(url = id)))
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { resource ->
//                when (resource) {
//                    Resource.Loading -> { }
//
//                    is Resource.Data -> exit.postValue(true)
//
//                    is Resource.Error -> { }
//                }
//            }
//            .addTo(disposables)
//
//    }

}
