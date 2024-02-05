package ru.nurguru.recipesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private lateinit var categoriesAdapter: CategoriesListAdapter
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
        binding.rvCategories.adapter = CategoriesListAdapter(
            dataSet = STUB.getCategories(), this
        )
        categoriesAdapter = CategoriesListAdapter(dataSet = STUB.getCategories(), this)
        //я понимаю что шляпа вышла, но я уже два дня торможу поэтому пришлю что есть
        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })
    }

    private fun openRecipesByCategoryId() = childFragmentManager.commit {
        replace<RecipesListFragment>(R.id.mainContainer)
        setReorderingAllowed(true)
        addToBackStack(null)
    }
}



