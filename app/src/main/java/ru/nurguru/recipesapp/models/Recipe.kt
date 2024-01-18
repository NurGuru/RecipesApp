package ru.nurguru.recipesapp.models

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredients>,
    val method: List<String>,
    val imageUrl: String,
)
