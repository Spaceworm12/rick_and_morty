package com.example.rickandmorty.data.network.category


//Это для чего?
data class Category(
    val name: String,
    val type: CategoryType
)
enum class CategoryType {
    CATEGORY_CHARACTERS,
    CATEGORY_LOCATIONS,
    CATEGORY_EPISODES,
}
