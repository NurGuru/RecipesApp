package ru.nurguru.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.serialization.json.Json
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ActivityMainBinding
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants.URL_GET_CATEGORIES
import ru.nurguru.recipesapp.model.Constants.URL_GET_RECIPES_SUFFIX
import ru.nurguru.recipesapp.model.Recipe
import java.net.HttpURLConnection
import java.net.URL
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var categoriesIds: List<Int> = listOf()

        val categoriesThread = Thread {
            val categoriesUrl = URL(URL_GET_CATEGORIES)
            val categoriesConnection: HttpURLConnection =
                categoriesUrl.openConnection() as HttpURLConnection
            categoriesConnection.connect()

            val deserializedCategoryList = Json.decodeFromString<List<Category>>(
                categoriesConnection.inputStream.bufferedReader().readText()
            )

            categoriesIds = deserializedCategoryList.map { it.id }

            Log.i("!!!", "responseCode: ${categoriesConnection.responseCode}")
            Log.i("!!!", "responseMessage: ${categoriesConnection.responseMessage}")
            Log.i("!!!", "CategoriesList: $deserializedCategoryList")
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

        }
        categoriesThread.start()

        Thread.sleep(1000)
        val threadPool = Executors.newFixedThreadPool(10)

        categoriesIds.forEach { id ->
            threadPool.execute {
                val recipeUrl = URL("$URL_GET_CATEGORIES/$id/$URL_GET_RECIPES_SUFFIX")
                val recipeConnection: HttpURLConnection =
                    recipeUrl.openConnection() as HttpURLConnection
                recipeConnection.connect()
                val deserializedRecipesList = Json.decodeFromString<List<Recipe>>(
                    recipeConnection.inputStream.bufferedReader().readText()
                )

                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                Log.i("!!!", "RecipesList: $deserializedRecipesList")
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