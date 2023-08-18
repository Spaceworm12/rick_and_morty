package com.example.rickandmorty.presentation.listperson

import com.example.rickandmorty.presentation.model.modelperson.Person

sealed class PersonListEvents {
    class AddToFavorite(val person: Person) : PersonListEvents()
    class DeleteFromFavorites(val id: Int) : PersonListEvents()
    class CheckStatus(val person: Person) : PersonListEvents()
}
