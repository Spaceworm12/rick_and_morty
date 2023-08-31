package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.Screen

interface Coordinator {

    fun goTo(screen: Screen)

    fun goToDistinct(screen: Screen)

    fun goBack()

    fun goBackTo(screen: Screen)

    fun replace(screen: Screen)

    fun newRootScreen(screen: Screen)

    fun clearStack()

}
