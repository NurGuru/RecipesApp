package ru.nurguru.recipesapp.data

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants.BASE_URL
import ru.nurguru.recipesapp.model.Constants.CONTENT_TYPE
import ru.nurguru.recipesapp.model.Constants.IMAGES_URL
import ru.nurguru.recipesapp.model.Recipe
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RecipesRepository  @Inject constructor(
    private val recipesDao: RecipesDao,
    private val categoriesDao: CategoriesDao,
    private val recipeApiService: RecipeApiService,
) {

    private val ioDispatcher: CoroutineContext=Dispatchers.IO

    suspend fun getCategoriesFromCache(): List<Category> =
        withContext(Dispatchers.IO) {
            categoriesDao.getCategories()
        }

    suspend fun addCategoriesToCache(categories: List<Category>) {
        withContext(ioDispatcher) {
            categoriesDao.addCategories(categories)
        }
    }

    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int) = withContext(Dispatchers.IO) {
        recipesDao.getRecipesByCategoryId(categoryId)
    }

    suspend fun addRecipeListToCache(recipeList: List<Recipe>) =
        withContext(ioDispatcher) { recipesDao.addRecipes(recipeList) }

    suspend fun getFavoriteRecipesFromCache() =
        withContext(ioDispatcher) { recipesDao.getFavoriteRecipes() }

    suspend fun getRecipeByRecipeIdFromCache(recipeId: Int) =
        withContext(ioDispatcher) { recipesDao.getRecipeById(recipeId) }

    suspend fun updateRecipeInCache(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipesDao.updateRecipe(recipe)
    }

    suspend fun getCategories(): List<Category>? {
        var categories: List<Category>? = null

        return withContext(ioDispatcher) {
            try {
                val categoriesCall: Call<List<Category>> = recipeApiService.getCategories()
                val categoriesResponse: Response<List<Category>> = categoriesCall.execute()

                categories = categoriesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }

                Log.i("!!!", "categories: ${categories.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            } catch (e: Exception) {
                Log.i("network, getCategories()", "${e.printStackTrace()}")
            }
            return@withContext categories
        }
    }

    suspend fun getRecipesByCategoryId(categoryId: Int): List<Recipe>? {
        var recipes: List<Recipe>? = null

        return withContext(ioDispatcher) {
            try {
                val recipesCall: Call<List<Recipe>> = recipeApiService.getRecipesByCategoryId(categoryId)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }
                Log.i("!!!", "recipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByCategoryId()", "${e.printStackTrace()}")
            }
            return@withContext recipes
        }
    }

    suspend fun getRecipesByIds(idsSet: Set<Int>): List<Recipe>? {
        var recipes: List<Recipe>? = null

        return withContext(ioDispatcher) {
            try {
                val idsString = idsSet.joinToString(separator = ",")

                val recipesCall: Call<List<Recipe>> = recipeApiService.getRecipesByIds(idsString)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }
                Log.i("!!!", "favoriteRecipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByIds()", "${e.printStackTrace()}")
            }
            return@withContext recipes
        }
    }


    suspend fun getRecipeById(recipeId: Int): Recipe? {
        var recipe: Recipe?

        return withContext(ioDispatcher) {
            try {
                val recipeCall: Call<Recipe> = recipeApiService.getRecipeById(recipeId)
                val recipeResponse: Response<Recipe> = recipeCall.execute()

                recipe = recipeResponse.body()
                Log.i("!!!", "recipes: ${recipe.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                recipe
            } catch (e: Exception) {
                Log.i("network, getRecipeById()", "${e.printStackTrace()}")
                null
            }
        }

    }

    suspend fun getCategoryById(categoryId: Int): Category? {
        var category: Category?
        return withContext(ioDispatcher) {
            try {
                val categoryCall: Call<Category> = recipeApiService.getCategoryById(categoryId)
                val categoryResponse: Response<Category> = categoryCall.execute()

                category = categoryResponse.body()

                Log.i("!!!", "category: ${category.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                category
            } catch (e: Exception) {
                Log.i("network, getCategoryById()", "${e.printStackTrace()}")
                null
            }
        }
    }
}


