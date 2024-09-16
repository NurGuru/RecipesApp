package ru.nurguru.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ActivityMainBinding
import ru.nurguru.recipesapp.ui.categories.CategoriesListFragment
import ru.nurguru.recipesapp.ui.recipes.favorites.FavoritesFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navBtnFavorite.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.favoritesFragment2)
        }

        binding.navBtnCategories.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate((R.id.categoriesListFragment))
        }
    }
}