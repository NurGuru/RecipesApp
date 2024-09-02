package ru.nurguru.recipesapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.data.Constants.TAG_RECIPE_VIEW_MODEL
import ru.nurguru.recipesapp.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var numberOfPortions: Int = 1,
    var isInFavorites: Boolean = false
)

class RecipeViewModel : ViewModel() {
    private var mutableRecipeUiState: MutableLiveData<RecipeUiState> = MutableLiveData()
    val recipeUiState: LiveData<RecipeUiState> = mutableRecipeUiState


    init {
        Log.i(TAG_RECIPE_VIEW_MODEL, "RecipeViewModel init block")
        mutableRecipeUiState.value = RecipeUiState(isInFavorites = true)
    }
}