package ru.nurguru.recipesapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nurguru.recipesapp.model.Constants.SHARED_FAVORITES_IDS_FILE_NAME
import ru.nurguru.recipesapp.model.Constants.SHARED_FAVORITES_IDS_KEY
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Recipe

data class FavoritesUiState(
    val recipeList: List<Recipe> = listOf()
)

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private var _favoritesUiState: MutableLiveData<FavoritesUiState> =
        MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    private val sharedPrefs by lazy {
        application.getSharedPreferences(
            SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    fun loadFavorites() {
        _favoritesUiState.value =
            _favoritesUiState.value?.copy(recipeList = STUB.getRecipesByIds(getFavoritesIds()))
    }

    private fun getFavoritesIds(): Set<Int> {
        val setOfFavoritesIds =
            sharedPrefs?.getStringSet(SHARED_FAVORITES_IDS_KEY, setOf()) ?: setOf()

        return setOfFavoritesIds.map { it.toInt() }.toSet()
    }
}