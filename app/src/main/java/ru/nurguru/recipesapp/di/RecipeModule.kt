package ru.nurguru.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Provides
    fun provideDataBAse(@ApplicationContext context: Context): RecipesDatabase =
        Room.databaseBuilder(
            context,
            RecipesDatabase::class.java,
            "Database2"
        ).build()

    @Provides
    fun provideCategoriesDao(recipesDatabase: RecipesDatabase): CategoriesDao =
        recipesDatabase.categoriesDao()

    @Provides
    fun provideRecipesDao(recipesDatabase: RecipesDatabase): RecipesDao =
        recipesDatabase.recipesDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return client
    }

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
      val contentType = CONTENT_TYPE.toMediaType()
      val retrofit = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(Json.asConverterFactory(contentType))
          .client(okHttpClient)
          .build()
      return retrofit
  }

    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)

//    val repository = RecipesRepository(
//        categoriesDao = categoriesDao,
//        recipesDao = recipesDao,
//        ioDispatcher = ioDispatcher,
//        recipeApiService = recipeApiService
//    )
//
//    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
//    val FavoritesViewModelFactory = FavoritesViewModelFactory(repository)
//    val RecipesListViewModelFactory = RecipesListViewModelFactory(repository)
//    val RecipeViewModelFactory = RecipeViewModelFactory(repository)
}