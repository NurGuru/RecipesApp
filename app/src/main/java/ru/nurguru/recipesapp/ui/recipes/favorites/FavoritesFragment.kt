package ru.nurguru.recipesapp.ui.recipes.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentFavoritesBinding
import ru.nurguru.recipesapp.model.Recipe
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListAdapter

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentFavoritesBinding is null!")

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val recipesListAdapter = RecipesListAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        favoritesViewModel.loadFavorites()
        favoritesViewModel.favoritesUiState.observe(viewLifecycleOwner) { favoritesState ->
            if (favoritesState.recipeList == null) {
                Toast.makeText(requireContext(), R.string.data_loading_toast, Toast.LENGTH_LONG)
                    .show()
            } else {
                if (favoritesState.recipeList.isNotEmpty()) {
                    binding.tvFavoritesStub.visibility = View.GONE
                    binding.rvFavorites.visibility = View.VISIBLE

                    recipesListAdapter.dataSet = favoritesState.recipeList
                    recipesListAdapter.notifyDataSetChanged()
                } else {
                    binding.tvFavoritesStub.visibility = View.VISIBLE
                    binding.rvFavorites.visibility = View.GONE
                }
            }

        }

        recipesListAdapter.setOnItemClickListener(
            object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipe: Recipe) {
                    openRecipeByRecipeId(recipe)
                }
            }
        )
        binding.rvFavorites.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipe: Recipe) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipe)
        )
    }
}