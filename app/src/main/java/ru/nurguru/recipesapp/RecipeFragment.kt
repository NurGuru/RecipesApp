package ru.nurguru.recipesapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding
import ru.nurguru.recipesapp.models.Recipe


class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private var recipe: Recipe? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        binding.textView.text = recipe?.title
    }

    private fun initBundleData() {
        if (Build.VERSION.SDK_INT >= 33) {
            arguments.let {
                recipe = requireArguments().getParcelable(Constants.ARG_RECIPE, Recipe::class.java)
            }
        } else {
            arguments.let {
                recipe = requireArguments().getParcelable(Constants.ARG_RECIPE)
            }
        }



    }
}