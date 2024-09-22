package ru.nurguru.recipesapp.ui.recipes.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.model.Constants
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding
import ru.nurguru.recipesapp.model.Constants.TAG_RECIPE_VIEW_MODEL
import ru.nurguru.recipesapp.model.Recipe
import java.io.InputStream


class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private var recipe: Recipe? = null
    private var ivRecipeItemImage: String? = null
    private var isInFavorites: Boolean = false
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")
    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initRecycler()
        initUI()
        setLiveDataObserver()
    }

    private fun setLiveDataObserver(){
        viewModel.recipeUiState.observe(viewLifecycleOwner) {
            Log.i(TAG_RECIPE_VIEW_MODEL, "$it")
        }
    }

    private fun initRecycler() {
        val ingredientAdapter = recipe?.let { IngredientsAdapter(it.ingredients) }
        binding.rvIngredients.adapter = ingredientAdapter

        val methodAdapter = recipe?.let { MethodAdapter(it.method) }
        binding.rvMethod.adapter = methodAdapter

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ingredientAdapter?.updateIngredients(progress)
                binding.portionsCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
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

    private fun initUI() {

        ivRecipeItemImage = recipe?.imageUrl

        val inputStream: InputStream? = ivRecipeItemImage?.let { view?.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipeItemImage.setImageDrawable(drawable)

        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }

        with(binding) {
            tvRecipeSubTitle.text = recipe?.title
            rvIngredients.addItemDecoration(dividerItemDecoration)
            rvMethod.addItemDecoration(dividerItemDecoration)
        }

        val favoritesIdStringSet = getFavorites()

        recipe?.let { recipe ->
            if (recipe.id.toString() in favoritesIdStringSet) isInFavorites = true

            with(binding.ibFavoritesIcon) {
                setImageResource(
                    if (isInFavorites) R.drawable.ic_heart
                    else R.drawable.ic_heart_empty,
                )

                setOnClickListener {
                    if (isInFavorites) {
                        favoritesIdStringSet.remove(recipe.id.toString())
                        setImageResource(R.drawable.ic_heart_empty)
                        isInFavorites = false
                    } else {
                        favoritesIdStringSet.add(recipe.id.toString())
                        setImageResource(R.drawable.ic_heart)
                        isInFavorites = true
                    }

                    saveFavorites(favoritesIdStringSet)
                }
            }
        }
    }

    private fun saveFavorites(recipeIds: Set<String>) {
        val sharedPrefs = requireActivity().getSharedPreferences(
            Constants.FAVORITES_PREFERENCES, Context.MODE_PRIVATE
        )
        sharedPrefs?.edit()?.putStringSet(Constants.FAVORITES_KEY, recipeIds)?.apply()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = requireActivity().getSharedPreferences(
            Constants.FAVORITES_PREFERENCES, Context.MODE_PRIVATE
        )
        val setOfFavoritesId =
            sharedPrefs?.getStringSet(Constants.FAVORITES_KEY, setOf()) ?: setOf()

        return HashSet(setOfFavoritesId)
    }
}