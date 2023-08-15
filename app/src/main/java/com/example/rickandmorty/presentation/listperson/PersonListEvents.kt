package com.example.rickandmorty.presentation.listperson

import com.example.rickandmorty.presentation.model.modelperson.PersonDetail


sealed class PersonListEvents {
    class GetCurrentPerson(person: PersonDetail) : PersonListEvents()
}
