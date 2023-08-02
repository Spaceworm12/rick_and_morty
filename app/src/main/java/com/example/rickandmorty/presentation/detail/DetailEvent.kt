package com.example.rickandmorty.presentation.detail

import com.example.rickandmorty.presentation.model.ExampleModel


sealed class DetailEvent {
    class SetItem(val item: ExampleModel): DetailEvent()
    class SaveItem(val id: Long): DetailEvent()
}
