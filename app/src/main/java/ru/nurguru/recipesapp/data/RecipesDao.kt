package ru.nurguru.recipesapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.nurguru.recipesapp.model.Constants.RECIPE
import ru.nurguru.recipesapp.model.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM $RECIPE")
    fun getRecipes(): List<Recipe>

    @Query("SELECT*FROM $RECIPE WHERE categoryId = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipes(recipeList: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeToFavoritesList(favoriteRecipeList: List<Recipe>)

    @Query("SELECT * FROM $RECIPE WHERE isFavorite = 1")
    fun getFavoriteRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Recipe

    @Update
    fun updateRecipe(recipe: Recipe)
}