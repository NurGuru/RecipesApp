package ru.nurguru.recipesapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numberOfPortions: Int = 1,
        val isInFavorites: Boolean = false
    )
}