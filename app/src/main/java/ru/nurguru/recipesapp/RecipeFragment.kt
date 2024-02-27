package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding
import ru.nurguru.recipesapp.models.Recipe
import java.io.InputStream


class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private var recipe: Recipe? = null
    private var ivRecipeItemImage: String? = null
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
        initRecycler()
        initUI()
    }

    private fun initRecycler() {
        val ingredientAdapter = recipe?.let { IngredientsAdapter( it.ingredients) }
        binding.rvIngredients.adapter = ingredientAdapter

        val methodAdapter = MethodAdapter(listOf(recipe))
        binding.rvMethod.adapter = methodAdapter
    }

    private fun initBundleData() {

        arguments.let {
            ivRecipeItemImage = requireArguments().getString(Constants.ARG_RECIPE_IMAGE_URL)
        }

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

    private fun initUI(){
        binding.tvRecipeSubTitle.text = recipe?.title

        val inputStream: InputStream? =
            ivRecipeItemImage?.let { view?.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipeItemImage.setImageDrawable(drawable)
    }
}