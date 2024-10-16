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
import java.net.HttpURLConnection
import java.net.URL


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

        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.content

            val deserialize = Json.decodeFromString<List<Category>>(
                connection.inputStream.bufferedReader().readText()
            )

            Log.i("!!!", "responseCode: ${connection.responseCode}")
            Log.i("!!!", "responseMessage: ${connection.responseMessage}")
            Log.i("!!!", "responseCode: ${connection.inputStream.bufferedReader().readText()}")
            Log.i("!!!", "responseDeCode: $deserialize")
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
        }
        thread.start()

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