package ru.nurguru.recipesapp.di

import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListViewModel

class RecipesListViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<RecipesListViewModel> {
    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(recipesRepository)
    }
}