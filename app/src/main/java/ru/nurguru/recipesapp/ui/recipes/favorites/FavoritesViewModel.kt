package ru.nurguru.recipesapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Constants.SHARED_FAVORITES_IDS_FILE_NAME
import ru.nurguru.recipesapp.model.Constants.SHARED_FAVORITES_IDS_KEY
import ru.nurguru.recipesapp.model.Recipe


class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    data class FavoritesUiState(
        val recipeList: List<Recipe>? = listOf()
    )

    private var _favoritesUiState: MutableLiveData<FavoritesUiState> =
        MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    private val sharedPrefs by lazy {
        application.getSharedPreferences(
            SHARED_FAVORITES_IDS_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    private val recipesRepository = RecipesRepository(application)

    fun loadFavorites() {
        viewModelScope.launch {
            val cache = recipesRepository.getFavoriteRecipesFromCache()
            val remote = recipesRepository.getRecipesByIds(getFavoritesIds())

            remote?.let {
                recipesRepository.addFavoritesRecipesListToCache(it.map { recipe ->
                    recipe.copy(isFavorite = true)
                }
                )
            }
            if (cache.isEmpty()) {
                _favoritesUiState.value =
                    _favoritesUiState.value?.copy(
                        recipeList = remote
                    )
            } else {
                _favoritesUiState.value =
                    _favoritesUiState.value?.copy(
                        recipeList = cache
                    )
            }
        }
    }


    private fun getFavoritesIds(): Set<Int> {
        val setOfFavoritesIds =
            sharedPrefs?.getStringSet(SHARED_FAVORITES_IDS_KEY, setOf()) ?: setOf()

        return setOfFavoritesIds.map { it.toInt() }.toSet()
    }
}