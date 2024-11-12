package ru.nurguru.recipesapp.di

import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(recipesRepository)
    }
}