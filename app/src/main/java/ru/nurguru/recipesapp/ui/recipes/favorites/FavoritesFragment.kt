package ru.nurguru.recipesapp.ui.recipes.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentFavoritesBinding
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListAdapter

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val viewModel: FavoritesViewModel by viewModels()
    private val recipesListAdapter = RecipesListAdapter(listOf())
    private val binding
        get() = _binding ?: throw IllegalArgumentException("FragmentFavoritesBinding is null!")

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
        viewModel.loadFavorites()
        viewModel.favoritesUiState.observe(viewLifecycleOwner) { favoritesState ->
            if (favoritesState.recipeList == null) {
                Toast.makeText(requireContext(), R.string.data_loading_toast, Toast.LENGTH_LONG).show()
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
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
        )
        binding.rvFavorites.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
        )
    }
}