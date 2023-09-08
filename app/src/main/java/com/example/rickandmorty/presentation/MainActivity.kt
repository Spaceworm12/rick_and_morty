package com.example.rickandmorty.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.application.App
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.presentation.category.CategoryListFragment
import com.example.rickandmorty.presentation.navigation.Coordinator
import com.example.rickandmorty.presentation.navigation.Screens
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder = App.getNavigatorHolder()

    private val coordinator: Coordinator = App.getCoordinator()

    private val navigator =  AppNavigator(this, R.id.fragment_container)

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            coordinator.goTo(Screens.ListPersonsScreen())
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.fragment_container, CategoryListFragment())
//                .commit()

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}
