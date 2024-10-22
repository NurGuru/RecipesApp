package ru.nurguru.recipesapp.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

    private val threadPool = Executors.newFixedThreadPool(10)

    fun getCategories(callback: (List<Category>?) -> Unit) {
        var categories: List<Category>? = null

        threadPool.execute {
            try {
                val categoriesCall: Call<List<Category>> = service.getCategories()
                val categoriesResponse: Response<List<Category>> = categoriesCall.execute()

                categories = categoriesResponse.body()
                callback(categories)

                Log.i("!!!", "categories: ${categories.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            } catch (e: Exception) {
                Log.i("network, getCategories()", "${e.printStackTrace()}")
                callback(null)
            }
        }

    }

    fun getRecipesByCategoryId(categoryId: Int, callback: (List<Recipe>?) -> Unit): List<Recipe>? {
        var recipes: List<Recipe>? = null

        threadPool.execute {
            try {
                val recipesCall: Call<List<Recipe>> = service.getRecipesByCategoryId(categoryId)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()
                callback(recipes)

                Log.i("!!!", "recipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByCategoryId()", "${e.printStackTrace()}")
                callback(null)
            }
        }

        return recipes
    }

    fun getRecipesByIds(idsSet: Set<Int>, callback: (List<Recipe>?) -> Unit): List<Recipe>? {
        var recipes: List<Recipe>? = null

        threadPool.execute {
            try {
                val idsString = idsSet.joinToString(separator = ",")

                val recipesCall: Call<List<Recipe>> = service.getRecipesByIds(idsString)
                val recipesResponse: Response<List<Recipe>> = recipesCall.execute()

                recipes = recipesResponse.body()
                callback(recipes)

                Log.i("!!!", "favoriteRecipes: ${recipes.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("network, getRecipesByIds()", "${e.printStackTrace()}")
                callback(null)
            }
        }

        return recipes
    }


    fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit): Recipe? {
        var recipe: Recipe? = null

        threadPool.execute {
            try {
                val recipeCall: Call<Recipe> = service.getRecipeById(recipeId)
                val recipeResponse: Response<Recipe> = recipeCall.execute()

                recipe = recipeResponse.body()
                callback(recipe)
                Log.i("!!!", "recipes: ${recipe.toString()}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            } catch (e: Exception) {
                Log.i("network, getRecipeById()", "${e.printStackTrace()}")
                callback(null)
            }
        }

        return recipe
    }

    fun getCategoryById(categoryId: Int): Category? {
        var category: Category? = null
        threadPool.execute {
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

        return category
    }
}


