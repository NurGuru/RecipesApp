package ru.nurguru.recipesapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.nurguru.recipesapp.data.RecipesRepository

import ru.nurguru.recipesapp.model.Category
import javax.inject.Inject

data class CategoriesUiState(
    val categoriesList: List<Category>? = listOf(),
)

@HiltViewModel
class CategoriesListViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {
    private var _categoriesUiState: MutableLiveData<CategoriesUiState> = MutableLiveData(
        CategoriesUiState()
    )
    val categoriesUiState: LiveData<CategoriesUiState> = _categoriesUiState

    fun loadCategories() {

        viewModelScope.launch {
            val cache = recipesRepository.getCategoriesFromCache()
            if (cache.isEmpty()) {
                val remote = recipesRepository.getCategories()
                remote?.let { recipesRepository.addCategoriesToCache(it) }
                _categoriesUiState.value = _categoriesUiState.value?.copy(
                    categoriesList = remote
                )
            } else {
                _categoriesUiState.value = _categoriesUiState.value?.copy(
                    categoriesList = cache
                )
            }
        }
    }
}


