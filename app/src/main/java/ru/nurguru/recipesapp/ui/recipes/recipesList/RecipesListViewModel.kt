package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe

data class RecipeListUiState(
    var category: Category? = null,
    var recipeList: List<Recipe> = listOf(),
    var recipeListImage: Drawable? = null
)

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private var mutableRecipeListUiState: MutableLiveData<RecipeListUiState> =
        MutableLiveData(RecipeListUiState())
    var recipeListUiState: LiveData<RecipeListUiState> = mutableRecipeListUiState

    fun loadRecipesList(categoryId: Int?) {
        mutableRecipeListUiState.value?.let {
            it.category = STUB.getCategories().find { category -> category.id == categoryId }
            it.recipeList = STUB.getRecipesByCategoryId(categoryId ?: 0)

            try {
                val inputStream = application.assets?.open(it.category?.imageUrl ?: "burger.png")
                it.recipeListImage = Drawable.createFromStream(inputStream, null)
            } catch (e: Exception) {
                Log.e(
                    "${application.getString((R.string.asset_error))}",
                    "${e.printStackTrace()}"
                )
            }
        }
    }
}