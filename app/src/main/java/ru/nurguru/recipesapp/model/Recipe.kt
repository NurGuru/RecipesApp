package ru.nurguru.recipesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
    var numberOfPortions: Int = 1,
    var isInFavorites: Boolean = false
):Parcelable
