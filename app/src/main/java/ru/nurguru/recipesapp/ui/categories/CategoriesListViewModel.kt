package ru.nurguru.recipesapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository

import ru.nurguru.recipesapp.model.Category

data class CategoriesUiState(
    val categoriesList: List<Category>? = listOf(),
    val isLoading: Boolean = true
)

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    private var _categoriesUiState: MutableLiveData<CategoriesUiState> = MutableLiveData(
        CategoriesUiState()
    )
    val categoriesUiState: LiveData<CategoriesUiState> = _categoriesUiState

    private val recipesRepository = RecipesRepository(application)

    fun loadCategories() {

        viewModelScope.launch {
            val cache = recipesRepository.getCategoriesFromCashe()
            _categoriesUiState.value = _categoriesUiState.value?.copy(
                categoriesList = cache,
                isLoading = cache.isEmpty()
            )
            if (cache.isEmpty()) {
                val remote = recipesRepository.getCategories()
                    ?.apply { recipesRepository.addCategories(this) }

                _categoriesUiState.value = _categoriesUiState.value?.copy(
                    categoriesList = remote,
                    isLoading = false
                )
            } else {
                _categoriesUiState.value = _categoriesUiState.value?.copy(
                    isLoading = false
                )
            }

        }
    }
}


