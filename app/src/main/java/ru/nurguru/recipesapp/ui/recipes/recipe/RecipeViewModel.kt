package ru.nurguru.recipesapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeUiState(
        var recipe: Recipe? = null,
        var numberOfPortions: Int = 1,
    )
}