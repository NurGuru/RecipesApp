package ru.nurguru.recipesapp.ui.categories

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
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding
import ru.nurguru.recipesapp.data.Constants
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListFragment

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")
    private val viewModel: CategoriesListViewModel by activityViewModels()
    private val categoriesListAdapter = CategoriesListAdapter(listOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        viewModel.loadCategories()
        viewModel.categoriesUiState.observe(viewLifecycleOwner) { categoriesState ->
            categoriesListAdapter.dataSet = categoriesState.categoriesList
            categoriesListAdapter.setOnItemClickListener(
                object : CategoriesListAdapter.OnItemClickListener {
                    override fun onItemClick(categoryId: Int) {
                        openRecipesByCategoryId(categoryId)
                    }
                }
            )
            binding.rvCategories.adapter = categoriesListAdapter

        }
    }


    private fun openRecipesByCategoryId(categoryId: Int) {
        val bundle = bundleOf(Constants.ARG_CATEGORY_ID to categoryId)
findNavController().navigate(R.id.recipesListFragment,bundle)
    }
}



