package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentListRecipesBinding
import ru.nurguru.recipesapp.model.Recipe

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private val viewModel: RecipesListViewModel by viewModels()
    private val recipeListAdapter = RecipesListAdapter(listOf())
    private val arg: RecipesListFragmentArgs by navArgs()
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
        viewModel.loadRecipesList(arg.category)
    }

    private fun initUI() {
        viewModel.recipeListUiState.observe(viewLifecycleOwner) { recipeListState ->
            if (recipeListState.recipesList == null) {
                Toast.makeText(requireContext(), R.string.data_loading_toast, Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.tvRecipeTitle.text = recipeListState.category?.title
                with(binding.ivRecipeMainImage) {
                    Glide.with(context)
                        .load(recipeListState.recipeListImageUrl)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(this)
                }
//                binding.ivRecipeMainImage.setImageDrawable(recipeListState.recipeListImage)
                recipeListAdapter.dataSet = recipeListState.recipesList
                recipeListAdapter.notifyDataSetChanged()
            }
        }
        initRecycler()
    }

    private fun initRecycler() {
        recipeListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                openRecipeByRecipeId(recipe)
            }
        })
        binding.rvRecipes.adapter = recipeListAdapter
        recipeListAdapter.notifyDataSetChanged()
    }

    private fun openRecipeByRecipeId(recipe: Recipe) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipe)
        )
    }
}