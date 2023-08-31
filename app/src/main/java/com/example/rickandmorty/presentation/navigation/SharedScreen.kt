package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.Screen
import java.time.LocalDate


sealed class SharedScreen : Screen {
    class DetailPersonScreen(val personId: Int) : SharedScreen()
    class DetailFavoritePersonScreen(val personId: Int) : SharedScreen()
}
