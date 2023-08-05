package com.example.rickandmorty.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.application.App
import com.example.rickandmorty.data.network.NetworkRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.presentation.model.modelcharacter.Character
import com.example.rickandmorty.presentation.model.Mapper

class DetailCharacterVewModel(
    private val networkRepository: NetworkRepositoryImpl = NetworkRepositoryImpl(App.getRickAndMortyApi())
): ViewModel() {
    private val disposables = CompositeDisposable()
    val character = MutableLiveData<Character>()
    val loading = MutableLiveData(false)

    init {
       loadCharacterInfo()
    }

    fun submitUIEvent(event: DetailCharacterEvent) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: DetailCharacterEvent) {
        when (event) {
            is DetailCharacterEvent.SetCharacter -> character.postValue((character.))
            is DetailCharacterEvent.LikeCharacter -> likeCharacter(id = event.id)
        }
    }

    private fun loadCharacterInfo() {
        networkRepository.getCharacterDetail(name,
            url: String,
            avatar: String,
            status: String,
            species: String,
            type: String,
            gender:String,
            id: Int)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    Resource.Loading -> loading.postValue(true)

                    is Resource.Data -> {
                        characters.postValue(resource.data ?: emptyList())
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
