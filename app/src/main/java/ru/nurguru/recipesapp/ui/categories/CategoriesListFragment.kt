package ru.nurguru.recipesapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding
import ru.nurguru.recipesapp.model.Category

@AndroidEntryPoint
class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")
    private val categoriesViewModel: CategoriesListViewModel by viewModels()
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
        categoriesViewModel.loadCategories()
        categoriesViewModel.categoriesUiState.observe(viewLifecycleOwner) { categoriesState ->
            if (categoriesState.categoriesList == null) {
                Toast.makeText(requireContext(), R.string.data_loading_toast, Toast.LENGTH_LONG)
                    .show()
            } else {
                categoriesListAdapter.dataSet = categoriesState.categoriesList
                categoriesListAdapter.notifyDataSetChanged()
            }
        }

        categoriesListAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(category: Category) {
                    openRecipesByCategoryId(category)
                }
            }
        )
        binding.rvCategories.adapter = categoriesListAdapter
    }

    private fun openRecipesByCategoryId(category: Category) {
        findNavController().navigate(
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                category
            )
        )
    }
}



