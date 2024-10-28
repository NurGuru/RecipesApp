package ru.nurguru.recipesapp.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import java.util.concurrent.Executors

class RecipesRepository {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()
    private val contentType = CONTENT_TYPE.toMediaType()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)


    suspend fun getCategories(): List<Category>? {
        var categories: List<Category>? = null

        withContext(Dispatchers.IO) {
            try {
                val categoriesCall: Call<List<Category>> = service.getCategories()
                val categoriesResponse: Response<List<Category>> = categoriesCall.execute()

                categories = categoriesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }

                Log.i("!!!", "categories: ${categories.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            } catch (e: Exception) {
                Log.i("network, getCategories()", "${e.printStackTrace()}")
            }
        }
        return withContext(Dispatchers.IO) {
            categories
        }
    }


    suspend fun getRecipesByCategoryId(categoryId: Int): List<Recipe>? {
        var recipes: List<Recipe>? = null

        withContext(Dispatchers.IO) {
            try {
                val recipesCall: Call<List<Recipe>> = service.getRecipesByCategoryId(categoryId)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }
                Log.i("!!!", "recipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByCategoryId()", "${e.printStackTrace()}")
            }
        }
        return withContext(Dispatchers.IO) { recipes }
    }

    suspend fun getRecipesByIds(idsSet: Set<Int>): List<Recipe>? {
        var recipes: List<Recipe>? = null

        withContext(Dispatchers.IO) {
            try {
                val idsString = idsSet.joinToString(separator = ",")

                val recipesCall: Call<List<Recipe>> = service.getRecipesByIds(idsString)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()?.map {
                    it.copy(imageUrl = "$IMAGES_URL/${it.imageUrl}")
                }
                Log.i("!!!", "favoriteRecipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByIds()", "${e.printStackTrace()}")
            }
        }
        return withContext(Dispatchers.IO) { recipes }
    }


    suspend fun getRecipeById(recipeId: Int): Recipe? {
        var recipe: Recipe? = null

        withContext(Dispatchers.IO) {
            try {
                val recipeCall: Call<Recipe> = service.getRecipeById(recipeId)
                val recipeResponse: Response<Recipe> = recipeCall.execute()

                recipe = recipeResponse.body()
                Log.i("!!!", "recipes: ${recipe.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipeById()", "${e.printStackTrace()}")
            }
        }
        return withContext(Dispatchers.IO) { recipe }
    }

    suspend fun getCategoryById(categoryId: Int): Category? {
        var category: Category? = null
        withContext(Dispatchers.IO) {
            try {
                val categoryCall: Call<Category> = service.getCategoryById(categoryId)
                val categoryResponse: Response<Category> = categoryCall.execute()

                category = categoryResponse.body()

                Log.i("!!!", "category: ${category.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getCategoryById()", "${e.printStackTrace()}")
            }
        }
        return withContext(Dispatchers.IO) { category }
    }
}


