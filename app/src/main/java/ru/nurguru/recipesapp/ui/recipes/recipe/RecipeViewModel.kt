package ru.nurguru.recipesapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Constants

import ru.nurguru.recipesapp.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var numberOfPortions: Int = 1,
    var isInFavorites: Boolean = false,

)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val favoritesIdStringSet = getFavorites()

    private var _recipeUiState: MutableLiveData<RecipeUiState> = MutableLiveData(RecipeUiState())
    val recipeUiState: LiveData<RecipeUiState> = _recipeUiState

    fun loadRecipe(recipeId: Int?) {
        // TODO: load from network
        _recipeUiState.value?.let {
            if (recipeId != null) {
                it.recipe = STUB.getRecipeById(recipeId = recipeId)
                it.isInFavorites = "$recipeId" in favoritesIdStringSet
            }
        }
    }

    fun onFavoritesClicked() {
        recipeUiState.value?.let {
            if (it.recipe != null && it.isInFavorites) {
                favoritesIdStringSet.remove("${it.recipe?.id}")
                it.isInFavorites = false
            } else {
                favoritesIdStringSet.add("${it.recipe?.id}")
                it.isInFavorites = true
            }

            saveFavorites(favoritesIdStringSet)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = application.getSharedPreferences(
            Constants.SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
        )
        val setOfFavoritesId =
            sharedPrefs?.getStringSet(Constants.SHARED_FAVORITES_IDS_KEY, setOf()) ?: setOf()

        return HashSet(setOfFavoritesId)
    }

    private fun saveFavorites(recipeIds: Set<String>) {
        val sharedPrefs = application.getSharedPreferences(
            Constants.SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
        )
        sharedPrefs?.edit()
            ?.putStringSet(Constants.SHARED_FAVORITES_IDS_KEY, recipeIds)
            ?.apply()
    }
}

