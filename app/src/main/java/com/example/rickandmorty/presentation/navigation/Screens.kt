package com.example.rickandmorty.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.rickandmorty.presentation.category.CategoryListFragment
import com.example.rickandmorty.presentation.detailperson.DetailPersonFragment
import com.example.rickandmorty.presentation.favorites.FavoritesListFragment
import com.example.rickandmorty.presentation.favoritesdetail.FavoritesDetailPersonFragment
import com.example.rickandmorty.presentation.listperson.PersonListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class Screens {
    class CategoryScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return CategoryListFragment()
        }
    }

    class ListPersonsScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = PersonListFragment()
        }

    class ListFavoritePersonsScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return FavoritesListFragment()
        }
    }

    class PersonScreen(val personId:Int) : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return DetailPersonFragment.newInstance(personId)
        }
    }

    class FavoritePersonScreen(val personId:Int) : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return FavoritesDetailPersonFragment.newInstance(personId)
        }
    }
}
