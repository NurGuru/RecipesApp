package ru.nurguru.recipesapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.model.Category

data class CategoriesUiState(
    var categoriesList: List<Category> = listOf()
) {

}

class CategoriesListViewModel() : ViewModel() {
    private var mutableCategoriesUiState: MutableLiveData<CategoriesUiState> = MutableLiveData(
        CategoriesUiState())
    val categoriesUiState:LiveData<CategoriesUiState> = mutableCategoriesUiState

fun loadCategories(){
    mutableCategoriesUiState.value?.categoriesList = STUB.getCategories()
}
}