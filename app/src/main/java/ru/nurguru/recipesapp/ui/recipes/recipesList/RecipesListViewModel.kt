package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe

data class RecipeListUiState(
    val category: Category? = null,
    val recipesList: List<Recipe>? = listOf(),
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private var _recipeListUiState: MutableLiveData<RecipeListUiState> =
        MutableLiveData(RecipeListUiState())
    var recipeListUiState: LiveData<RecipeListUiState> = _recipeListUiState
    private val recipesRepository = RecipesRepository()

    fun loadRecipesList(category: Category) {

        viewModelScope.launch {
            recipesRepository.getRecipesByCategoryId(category.id) { recipesList ->
                _recipeListUiState.postValue(
                    _recipeListUiState.value?.copy(
                        category = category,
                        recipesList = recipesList,
                    )
                )
            }
        }
    }
}
