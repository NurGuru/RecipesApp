package ru.nurguru.recipesapp.di

import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<RecipeViewModel> {
    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}