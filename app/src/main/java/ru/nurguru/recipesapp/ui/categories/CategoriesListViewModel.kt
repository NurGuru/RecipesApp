package ru.nurguru.recipesapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Category

data class CategoriesUiState(
    val categoriesList: List<Category> = listOf()
)

class CategoriesListViewModel() : ViewModel() {
    private var _categoriesUiState: MutableLiveData<CategoriesUiState> = MutableLiveData(
        CategoriesUiState()
    )
    val categoriesUiState: LiveData<CategoriesUiState> = _categoriesUiState

    fun loadCategories() {
        _categoriesUiState.value = _categoriesUiState.value?.copy(
            categoriesList = STUB.getCategories()
        )
    }
}