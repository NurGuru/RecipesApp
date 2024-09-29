package ru.nurguru.recipesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding
import ru.nurguru.recipesapp.model.Constants
import ru.nurguru.recipesapp.ui.categories.CategoriesListAdapter
import ru.nurguru.recipesapp.ui.recipes.recipesList.RecipesListFragment

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(dataSet = STUB.getCategories())
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories().find { it.id==categoryId }
        val categoryName = category?.title
        val categoryImageUrl = category?.imageUrl
        val bundle =
            bundleOf(
                Constants.ARG_CATEGORY_ID to categoryId,
                Constants.ARG_CATEGORY_NAME to categoryName,
                Constants.ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
            )


        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}



