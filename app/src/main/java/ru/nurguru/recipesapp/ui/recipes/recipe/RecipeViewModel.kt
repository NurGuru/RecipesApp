package ru.nurguru.recipesapp.ui.recipes.recipe

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
class RecipeViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numberOfPortions: Int = 1,
        val isInFavorites: Boolean = false,
    )

    private var _recipeUiState: MutableLiveData<RecipeUiState> = MutableLiveData(RecipeUiState())
    val recipeUiState: LiveData<RecipeUiState> = _recipeUiState

    fun loadRecipe(recipe: Recipe) {

        viewModelScope.launch {
            val cache = recipesRepository.getRecipeByRecipeIdFromCache(recipe.id)

            _recipeUiState.value = _recipeUiState.value?.copy(
                recipe = cache,
                isInFavorites = cache.isFavorite
            )
        }
    }

    fun onFavoritesClicked() {

        viewModelScope.launch {
            val recipeId = _recipeUiState.value?.recipe?.id
            val recipe = recipeId?.let { recipesRepository.getRecipeByRecipeIdFromCache(it) }
            if (recipe != null) {
                val isFavorite =
                    recipesRepository.getRecipeByRecipeIdFromCache(recipeId).isFavorite
                _recipeUiState.value = _recipeUiState.value?.copy(isInFavorites = !isFavorite)
                recipesRepository.updateRecipeInCache(recipe.copy(isFavorite = !isFavorite))
            }
        }
    }

    fun changePortionsCount(progress: Int) {
        _recipeUiState.value = _recipeUiState.value?.copy(numberOfPortions = progress)
    }
}

