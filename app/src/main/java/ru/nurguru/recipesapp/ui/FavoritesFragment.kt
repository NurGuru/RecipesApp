package ru.nurguru.recipesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.ui.Constants.ARG_RECIPE
import ru.nurguru.recipesapp.ui.Constants.FAVORITES_KEY
import ru.nurguru.recipesapp.ui.Constants.FAVORITES_PREFERENCES
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
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

        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val favoriteRecipesList = STUB.getRecipesByIds(getFavoritesIds())

        if (favoriteRecipesList.isNotEmpty()) {
            binding.tvFavoritesStub.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE

            val recipesListAdapter = RecipesListAdapter(dataSet = favoriteRecipesList)

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

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId )
        val bundle = bundleOf(ARG_RECIPE to recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("RecipeFragment")
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun getFavoritesIds(): Set<Int> {
        val sharedPrefs = activity?.getSharedPreferences(
            FAVORITES_PREFERENCES, Context.MODE_PRIVATE
        )
        val setOfFavoritesIds =
            sharedPrefs?.getStringSet(FAVORITES_KEY, setOf()) ?: setOf()

        return setOfFavoritesIds.map { it.toInt() }.toSet()
    }
}