package com.example.rickandmorty.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen


class SharedScreenMapper : (SharedScreen) -> FragmentScreen {

    override fun invoke(screen: SharedScreen): FragmentScreen = when (screen) {
        is SharedScreen.DetailPersonScreen -> Screens.PersonScreen(
            personId = screen.personId
        )

        is SharedScreen.DetailFavoritePersonScreen -> Screens.FavoritePersonScreen(
            personId = screen.personId
        )

    }
}
