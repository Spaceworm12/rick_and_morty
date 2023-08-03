package com.example.rickandmorty.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.presentation.listcharacter.CharacterListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, CharacterListFragment())
                .commit()

    }
}
