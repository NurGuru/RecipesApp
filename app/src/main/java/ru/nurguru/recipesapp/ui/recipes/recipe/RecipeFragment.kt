package ru.nurguru.recipesapp.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    private val viewModel: RecipeViewModel by activityViewModels()
    private val args: RecipeFragmentArgs by navArgs()

    private val ingredientAdapter: IngredientsAdapter = IngredientsAdapter(listOf())
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
        viewModel.loadRecipe(args.recipeId)
    }

    private fun initUI() {

        viewModel.recipeUiState.observe(viewLifecycleOwner) { recipeState ->

            binding.ivRecipeItemImage.setImageDrawable(recipeState.recipeImage)

            with(binding) {
                tvRecipeSubTitle.text = recipeState.recipe?.title
                portionsCount.text = recipeState.numberOfPortions.toString()
                seekBar.progress = recipeState.numberOfPortions
            }

            with(binding.ibFavoritesIcon) {
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (recipeState.isInFavorites) {
                            R.drawable.ic_heart
                        } else R.drawable.ic_heart_empty,
                        null
                    )
                )
            }

            ingredientAdapter.dataSet = recipeState.recipe?.ingredients ?: listOf()
            ingredientAdapter.updateIngredients(recipeState.numberOfPortions)
            binding.rvIngredients.adapter = ingredientAdapter
            ingredientAdapter.notifyDataSetChanged()

            methodAdapter.dataSet = recipeState.recipe?.method ?: listOf()
            binding.rvMethod.adapter = methodAdapter
            methodAdapter.notifyDataSetChanged()

        }


        binding.seekBar.setOnSeekBarChangeListener(
            PortionSeekBarListener { progress ->
                viewModel.changePortionsCount(progress)
            }
        )
        binding.ibFavoritesIcon.setOnClickListener {
            viewModel.onFavoritesClicked()
        }

        val dividerItemDecoration =
            DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.rvIngredients.addItemDecoration(dividerItemDecoration)
        binding.rvMethod.addItemDecoration(dividerItemDecoration)
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