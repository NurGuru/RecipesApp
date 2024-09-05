package ru.nurguru.recipesapp.ui.recipes.recipe

import android.graphics.drawable.Drawable
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
import ru.nurguru.recipesapp.data.Constants.ARG_RECIPE_ID
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    private var recipeId: Int? = null
    private val viewModel: RecipeViewModel by activityViewModels()

    private val ingredientsAdapter: IngredientsAdapter = IngredientsAdapter(listOf(), 1)
    private val methodAdapter: MethodAdapter = MethodAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initUI()
    }

    private fun initBundleData() {
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID)
        }
        viewModel.loadRecipe(recipeId)
    }

    private fun initUI() {

        viewModel.recipeUiState.observe(viewLifecycleOwner) { recipeState ->

            binding.ivRecipeItemImage.setImageDrawable(recipeState.recipeImage)

            with(binding) {
                tvRecipeSubTitle.text = recipeState.recipe?.title
                portionsCount.text = "${recipeState.recipe?.numberOfPortions ?: 1}"
                seekBar.progress = recipeState.recipe?.numberOfPortions ?: 1
            }

            with(binding.ibFavoritesIcon) {
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (recipeState.recipe != null && recipeState.isInFavorites) {
                            R.drawable.ic_heart
                        } else R.drawable.ic_heart_empty,
                        null
                    )
                )

                setOnClickListener {
                    viewModel.onFavoritesClicked()
                    if (recipeState.isInFavorites) {
                        setImageDrawable(
                            ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)
                        )
                    } else {
                        setImageDrawable(
                            ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_empty, null)
                        )
                    }
                }
            }

            val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.devider, null)?.let {
                dividerItemDecoration.setDrawable(it)
            }

            ingredientsAdapter.dataSet = recipeState.recipe?.ingredients ?: listOf()
            ingredientsAdapter.quantity = recipeState.recipe?.numberOfPortions ?: 1

            methodAdapter.dataSet = recipeState.recipe?.method ?: listOf()

            binding.seekBar.setOnSeekBarChangeListener(
                PortionSeekBarListener { progress ->
                    ingredientsAdapter.updateIngredients(progress)
                    viewModel.updateNumOfPortions(progress)
                    binding.portionsCount.text = "${recipeState.recipe?.numberOfPortions ?: 1}"

                }
            )
            binding.rvIngredients.adapter = ingredientsAdapter
            binding.rvMethod.adapter = methodAdapter

            binding.rvIngredients.addItemDecoration(dividerItemDecoration)
            binding.rvMethod.addItemDecoration(dividerItemDecoration)
        }
    }

    class PortionSeekBarListener(val onChangeIngredients: (Int) -> Unit) :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }
}