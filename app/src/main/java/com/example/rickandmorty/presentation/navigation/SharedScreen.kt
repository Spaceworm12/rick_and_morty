package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.Screen


sealed class SharedScreen : Screen {
    class DetailPersonScreen(val personId: Int) : SharedScreen()
    object ListPersonScreen : SharedScreen()
    object ListFavoritePersonScreen : SharedScreen()
    class DetailFavoritePersonScreen(val personId: Int) : SharedScreen()
    object CategoryScreen: SharedScreen()
}
