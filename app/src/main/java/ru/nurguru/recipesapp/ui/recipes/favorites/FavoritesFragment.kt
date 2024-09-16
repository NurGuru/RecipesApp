package ru.nurguru.recipesapp.ui.recipes.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.data.Constants.ARG_RECIPE
import ru.nurguru.recipesapp.data.Constants.SHARED_FAVORITES_IDS_FILE_NAME
import ru.nurguru.recipesapp.data.Constants.SHARED_FAVORITES_IDS_KEY
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.databinding.FragmentFavoritesBinding
import ru.nurguru.recipesapp.ui.recipes.recipe.RecipeFragment
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListAdapter

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val viewModel: FavoritesViewModel by activityViewModels()
    private val recipesListAdapter = RecipesListAdapter(listOf())
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentFavoritesBinding is null!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        viewModel.loadFavorites()
        viewModel.favoritesUiState.observe(viewLifecycleOwner) { favoritesState ->
            if (favoritesState.recipeList.isNotEmpty()) {
                binding.tvFavoritesStub.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE

                recipesListAdapter.dataSet = favoritesState.recipeList
                recipesListAdapter.setOnItemClickListener(
                    object : RecipesListAdapter.OnItemClickListener {
                        override fun onItemClick(recipeId: Int) {
                            openRecipeByRecipeId(recipeId)
                        }
                    }
                )
                binding.rvFavorites.adapter = recipesListAdapter
            } else {
                binding.tvFavoritesStub.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            }
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("RecipeFragment")
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }
}