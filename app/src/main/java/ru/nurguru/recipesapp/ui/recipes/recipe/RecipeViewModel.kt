package ru.nurguru.recipesapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.model.Constants.TAG_RECIPE_VIEW_MODEL
import ru.nurguru.recipesapp.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numberOfPortions: Int = 1,
        val isInFavorites: Boolean = false
    )
    private var _recipeUiState: MutableLiveData<RecipeUiState> = MutableLiveData(RecipeUiState()).also {
        state->
        Log.i(TAG_RECIPE_VIEW_MODEL, "RecipeViewModel init block")
        state.value = RecipeUiState(isInFavorites = true)
    }
    val recipeUiState: LiveData<RecipeUiState> = _recipeUiState
}