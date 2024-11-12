package ru.nurguru.recipesapp.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Recipe
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {

    data class FavoritesUiState(
        val recipeList: List<Recipe>? = listOf()
    )

    private var _favoritesUiState: MutableLiveData<FavoritesUiState> =
        MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    fun loadFavorites() {

        viewModelScope.launch {
            val cache = recipesRepository.getFavoriteRecipesFromCache()
            if (cache.isNotEmpty()) {
                _favoritesUiState.value =
                    _favoritesUiState.value?.copy(
                        recipeList = cache
                    )
            } else {
                _favoritesUiState.value =
                    _favoritesUiState.value?.copy(
                        recipeList = emptyList()
                    )
            }
        }
    }
}