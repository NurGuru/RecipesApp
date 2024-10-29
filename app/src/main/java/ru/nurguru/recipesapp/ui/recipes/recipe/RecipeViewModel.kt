package ru.nurguru.recipesapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nurguru.recipesapp.model.Constants
import ru.nurguru.recipesapp.model.Recipe


class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numberOfPortions: Int = 1,
        val isInFavorites: Boolean = false,
    )

    private val sharedPrefs by lazy {
        application.getSharedPreferences(
            Constants.SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    private var _recipeUiState: MutableLiveData<RecipeUiState> = MutableLiveData(RecipeUiState())
    val recipeUiState: LiveData<RecipeUiState> = _recipeUiState

    fun loadRecipe(recipe: Recipe) {
        // TODO: load from network
            _recipeUiState.value=
                _recipeUiState.value?.copy(
                    recipe = recipe,
                    isInFavorites = recipe.id.toString() in getFavorites(),
                )
    }

    fun onFavoritesClicked() {

        _recipeUiState.value?.let {
            val favoritesIdStringSet = getFavorites()
            if (it.recipe != null && it.isInFavorites) {
                favoritesIdStringSet.remove(it.recipe.id.toString())
                _recipeUiState.value = it.copy(isInFavorites = false)
            } else {
                favoritesIdStringSet.add(it.recipe?.id.toString())
                _recipeUiState.value = it.copy(isInFavorites = true)
            }
            saveFavorites(favoritesIdStringSet)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val setOfFavoritesId =
            sharedPrefs?.getStringSet(Constants.SHARED_FAVORITES_IDS_KEY, setOf()) ?: setOf()

        return HashSet(setOfFavoritesId)
    }

    private fun saveFavorites(recipeIds: Set<String>) {
        sharedPrefs?.edit()
            ?.putStringSet(Constants.SHARED_FAVORITES_IDS_KEY, recipeIds)
            ?.apply()
    }

    fun changePortionsCount(progress: Int) {
        _recipeUiState.value = _recipeUiState.value?.copy(numberOfPortions = progress)
    }
}

