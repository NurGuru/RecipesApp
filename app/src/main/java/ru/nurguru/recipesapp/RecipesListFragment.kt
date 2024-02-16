package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nurguru.recipesapp.databinding.FragmentListRecipesBinding
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

        categoryId = requireArguments().getInt("ARG_CATEGORY_ID")
        categoryName = requireArguments().getString("ARG_CATEGORY_NAME")
        categoryImageUrl = requireArguments().getString("ARG_CATEGORY_IMAGE_URL")

        binding.titleRecipes.text = categoryName

        val inputStream: InputStream? =
            this.context?.assets?.open(STUB.getCategories()[categoryId!!].imageUrl)//не уверен что тут именно это надо было с делать, поэтому пока использовал " !! "
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.burgerRecipesMainImage.setImageDrawable(drawable)
    }
}