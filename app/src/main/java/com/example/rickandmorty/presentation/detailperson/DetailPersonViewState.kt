package com.example.rickandmorty.presentation.detailperson

import com.example.rickandmorty.presentation.model.modelperson.PersonDetail


data class DetailPersonViewState(
    var isLoading: Boolean = false,
    val exit: Boolean = false,
    val errorText: String = "",
    val person: PersonDetail? = null,
)
