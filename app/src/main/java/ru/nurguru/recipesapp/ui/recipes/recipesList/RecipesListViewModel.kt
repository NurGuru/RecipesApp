package ru.nurguru.recipesapp.ui.recipes.recipesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe
import javax.inject.Inject

data class RecipeListUiState(
    val category: Category? = null,
    val recipesList: List<Recipe>? = listOf(),
)

@HiltViewModel
class RecipesListViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {

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

