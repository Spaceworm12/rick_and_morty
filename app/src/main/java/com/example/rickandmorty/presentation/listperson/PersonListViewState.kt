package com.example.rickandmorty.presentation.listperson

import com.example.rickandmorty.presentation.model.modelperson.Person

data class PersonListViewState(
    var isLoading: Boolean = false,
    val exit: Boolean = false,
    val persons: List<Person> = emptyList(),
    val person: Person? = null,
    val errorText: String = "",
)


