package ru.nurguru.recipesapp.di

import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.ui.categories.CategoriesListViewModel

class CategoriesListViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<CategoriesListViewModel> {
    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(recipesRepository)
    }
}