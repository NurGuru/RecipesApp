package ru.nurguru.recipesapp.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    private val recipeViewModel: RecipeViewModel by viewModels()
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
        recipeViewModel.loadRecipe(args.recipe)
    }

    private fun initUI() {

        recipeViewModel.recipeUiState.observe(viewLifecycleOwner) { recipeState ->
            with(binding.ivRecipeItemImage) {
                Glide.with(context)
                    .load(recipeState.recipe?.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .into(this)
            }


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
                recipeViewModel.changePortionsCount(progress)
            }
        )
        binding.ibFavoritesIcon.setOnClickListener {
            recipeViewModel.onFavoritesClicked()
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