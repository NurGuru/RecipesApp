package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentListRecipesBinding
import ru.nurguru.recipesapp.data.Constants
import ru.nurguru.recipesapp.data.Constants.ARG_RECIPE_ID
import ru.nurguru.recipesapp.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private var categoryId: Int? = null
    private val viewModel: RecipesListViewModel by activityViewModels()
    private val recipeListAdapter = RecipesListAdapter(listOf())
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initUI()
    }

    private fun initBundleData() {
        arguments.let {
            categoryId = requireArguments().getInt(Constants.ARG_CATEGORY_ID)
        }
        viewModel.loadRecipesList(categoryId)
    }

    private fun initUI() {
        viewModel.recipeListUiState.observe(viewLifecycleOwner) { recipeListState ->
            binding.tvRecipeTitle.text = recipeListState.category?.title
            binding.ivRecipeMainImage.setImageDrawable(recipeListState.recipeListImage)
            initRecycler(recipeListState)
        }
    }

    private fun initRecycler(recipeListState: RecipeListUiState) {
        recipeListAdapter.dataSet = recipeListState.recipeList

        recipeListAdapter.setOnItemClickListener(
            object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            })
        binding.rvRecipes.adapter = recipeListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val bundle = bundleOf(ARG_RECIPE_ID to recipeId)
        findNavController().navigate(R.id.recipeFragment, bundle)
    }
}