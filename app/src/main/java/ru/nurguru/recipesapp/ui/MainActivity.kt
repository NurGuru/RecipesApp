package ru.nurguru.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ActivityMainBinding
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants.URL_GET_CATEGORIES
import ru.nurguru.recipesapp.model.Constants.URL_GET_RECIPES_SUFFIX
import ru.nurguru.recipesapp.model.Recipe
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private val navOptions = NavOptions.Builder().setLaunchSingleTop(true)
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim).build()

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var categoriesIds: List<Int>
        val threadPool = Executors.newFixedThreadPool(10)

        threadPool.execute {

            val categoryRequest: Request = Request.Builder().url(URL_GET_CATEGORIES).build()

            val deserializedCategoryList = Json.decodeFromString<List<Category>>(
                client.newCall(categoryRequest)
                    .execute().body?.string().toString()
            )
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            categoriesIds = deserializedCategoryList.map { it.id }

            categoriesIds.forEach { id ->
                threadPool.execute {
                    val recipeRequest: Request =
                        Request.Builder().url("$URL_GET_CATEGORIES/$id/$URL_GET_RECIPES_SUFFIX")
                            .build()

                    val deserializedRecipesList = Json.decodeFromString<List<Recipe>>(
                        client.newCall(recipeRequest)
                            .execute().body?.string().toString()
                    )
                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                }
            }
        }

        binding.navBtnFavorite.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(
                R.id.favoritesFragment,
                null,
                navOptions,
            )
        }

        binding.navBtnCategories.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(
                R.id.categoriesListFragment,
                null,
                navOptions
            )
        }
    }
}