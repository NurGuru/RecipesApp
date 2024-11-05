package ru.nurguru.recipesapp.data

import androidx.room.Dao
import androidx.room.Delete
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

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
    @Query("SELECT*FROM $RECIPE WHERE categoryId = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipes(recipeList: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeToFavoritesList(favoriteRecipeList: List<Recipe>)

    @Query("SELECT * FROM $RECIPE WHERE isFavorite = 1")
    fun getFavoriteRecipes(): List<Recipe>

//    @Update
//    fun updateFavoritesRecipeList(favoriteRecipeList:List<Recipe>)
//
//    @Delete
//    fun delete(user: User)
}