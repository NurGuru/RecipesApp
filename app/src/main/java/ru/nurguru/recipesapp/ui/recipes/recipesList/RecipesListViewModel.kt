package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe

data class RecipeListUiState(
    val category: Category? = null,
    val recipesList: List<Recipe>? = listOf(),
)

class RecipesListViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private var _recipeListUiState: MutableLiveData<RecipeListUiState> =
        MutableLiveData(RecipeListUiState())
    var recipeListUiState: LiveData<RecipeListUiState> = _recipeListUiState

    fun loadRecipesList(category: Category) {

        viewModelScope.launch {

            val cache = recipesRepository.getRecipesByCategoryIdFromCache(category.id)

            if (cache != null) {
                if (cache.isEmpty()) {
                    val remote = recipesRepository.getRecipesByCategoryId(category.id)
                    remote?.let {
                        recipesRepository.addRecipeListToCache(it.map { recipe ->
                            recipe.copy(categoryId = category.id)
                        })
                    }
                    _recipeListUiState.value = _recipeListUiState.value?.copy(
                        category = category,
                        recipesList = remote
                    )
                } else {
                    _recipeListUiState.value = _recipeListUiState.value?.copy(
                        category = category,
                        recipesList = cache
                    )
                }
            }
        }
    }
}

