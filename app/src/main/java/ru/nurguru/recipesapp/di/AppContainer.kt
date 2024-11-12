package ru.nurguru.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.nurguru.recipesapp.data.CategoriesDao
import ru.nurguru.recipesapp.data.RecipeApiService
import ru.nurguru.recipesapp.data.RecipesDao
import ru.nurguru.recipesapp.data.RecipesDatabase
import ru.nurguru.recipesapp.data.RecipesRepository
import ru.nurguru.recipesapp.model.Constants.BASE_URL
import ru.nurguru.recipesapp.model.Constants.CONTENT_TYPE

class AppContainer(context: Context) {


    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()
    private val contentType = CONTENT_TYPE.toMediaType()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(client)
        .build()


    private val database = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        "Database2"
    ).build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val categoriesDao: CategoriesDao = database.categoriesDao()
    private val recipesDao: RecipesDao = database.recipesDao()

    private val recipeApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    val repository = RecipesRepository(
        categoriesDao = categoriesDao,
        recipesDao = recipesDao,
        ioDispatcher = ioDispatcher,
        recipeApiService = recipeApiService
    )

    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
    val FavoritesViewModelFactory = FavoritesViewModelFactory(repository)
    val RecipesListViewModelFactory = RecipesListViewModelFactory(repository)
    val RecipeViewModelFactory = RecipeViewModelFactory(repository)
}