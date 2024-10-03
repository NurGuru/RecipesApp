package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe

data class RecipeListUiState(
    val category: Category? = null,
    val recipeList: List<Recipe> = listOf(),
    val recipeListImage: Drawable? = null
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private var _recipeListUiState: MutableLiveData<RecipeListUiState> =
        MutableLiveData(RecipeListUiState())
    var recipeListUiState: LiveData<RecipeListUiState> = _recipeListUiState

    fun loadRecipesList(categoryId: Int?) {
        _recipeListUiState.value =
            _recipeListUiState.value?.copy(
                category = STUB.getCategories().find { category -> category.id == categoryId },
                recipeList = STUB.getRecipesByCategoryId(categoryId ?: 0)
            )

        try {
            val inputStream = application.assets?.open(
                _recipeListUiState.value?.category?.imageUrl ?: "burger.png"
            )
            _recipeListUiState.value = _recipeListUiState.value?.copy(
                recipeListImage = Drawable.createFromStream(
                    inputStream,
                    null
                )
            )
        } catch (e: Exception) {
            Log.e(
                application.getString((R.string.asset_error)),
                "${e.printStackTrace()}"
            )
        }

    }
}