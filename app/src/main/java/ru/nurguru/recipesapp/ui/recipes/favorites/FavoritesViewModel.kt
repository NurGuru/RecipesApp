package ru.nurguru.recipesapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.data.Constants.SHARED_FAVORITES_IDS_FILE_NAME
import ru.nurguru.recipesapp.data.Constants.SHARED_FAVORITES_IDS_KEY
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Recipe

data class FavoritesUiState(
    var recipeList: List<Recipe> = listOf()
)

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private var mutableFavoritesUiState: MutableLiveData<FavoritesUiState> =
        MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = mutableFavoritesUiState

    fun loadFavorites() {
        mutableFavoritesUiState.value?.recipeList = STUB.getRecipesByIds(getFavoritesIds())
    }

        private fun getFavoritesIds(): Set<Int> {
            val sharedPrefs = application.getSharedPreferences(
                SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
            )
            val setOfFavoritesIds =
                sharedPrefs?.getStringSet(SHARED_FAVORITES_IDS_KEY, setOf()) ?: setOf()

            return setOfFavoritesIds.map { it.toInt() }.toSet()
    }
}