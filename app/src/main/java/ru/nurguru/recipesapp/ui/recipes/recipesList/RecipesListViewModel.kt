package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants
import ru.nurguru.recipesapp.model.Recipe

data class RecipeListUiState(
    val category: Category? = null,
    val recipesList: List<Recipe>? = listOf(),
    val recipeListImage: Drawable? = null
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private var _recipeListUiState: MutableLiveData<RecipeListUiState> =
        MutableLiveData(RecipeListUiState())
    var recipeListUiState: LiveData<RecipeListUiState> = _recipeListUiState
    private val recipesRepository = RecipesRepository()

    fun loadRecipesList(category: Category) {
        _recipeListUiState.value =
            _recipeListUiState.value?.copy(
                category = category,
                recipesList = recipesRepository.getRecipesByCategoryId(category.id)
            )

        try {
            val inputStream = application.assets?.open(
                _recipeListUiState.value?.category?.imageUrl ?: Constants.DEFAULT_IMAGE
            )
            _recipeListUiState.value = _recipeListUiState.value?.copy(
                recipeListImage = Drawable.createFromStream(
                    inputStream,
                    null
                )
            )
        } catch (e: Exception) {
            Log.e(
                " asset error",
                "${e.printStackTrace()}"
            )
        }

    }
}