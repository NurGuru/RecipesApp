package ru.nurguru.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBtnFavorite.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.favoritesFragment,null, navOptions )
        }

        binding.navBtnCategories.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.categoriesListFragment,null, navOptions)
        }
    }
}