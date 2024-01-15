package ru.nurguru.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
 import ru.nurguru.recipesapp.databinding.ActivityMainBinding
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(R.id.mainContainer)
        }
    }
}