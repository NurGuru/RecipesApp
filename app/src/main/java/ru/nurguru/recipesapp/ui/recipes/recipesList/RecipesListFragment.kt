package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.data.STUB
import ru.nurguru.recipesapp.databinding.FragmentListRecipesBinding
import ru.nurguru.recipesapp.model.Constants
import ru.nurguru.recipesapp.ui.recipes.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
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
        initRecycler()
        initUI()
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(
            dataSet = STUB.getRecipesByCategoryId(
                categoryId = requireArguments().getInt(Constants.ARG_CATEGORY_ID)
            )
        )
        binding.rvRecipes.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {

        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(
            Constants.ARG_RECIPE to recipe,
        )

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun initBundleData() {
        arguments.let {
            categoryId = requireArguments().getInt(Constants.ARG_CATEGORY_ID)
            categoryName = requireArguments().getString(Constants.ARG_CATEGORY_NAME)
            categoryImageUrl = requireArguments().getString(Constants.ARG_CATEGORY_IMAGE_URL)
        }
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = categoryName
        val inputStream: InputStream? =
            categoryImageUrl?.let { view?.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipeMainImage.setImageDrawable(drawable)
    }
}