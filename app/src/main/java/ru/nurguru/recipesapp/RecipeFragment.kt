package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.databinding.FragmentRecipeBinding
import ru.nurguru.recipesapp.models.Recipe
import java.io.InputStream


class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private var recipe: Recipe? = null
    private var ivRecipeItemImage: String? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initRecycler()
        initUI()
    }

    private fun initRecycler() {
        val ingredientAdapter = recipe?.let { IngredientsAdapter(it.ingredients) }
        binding.rvIngredients.adapter = ingredientAdapter

        val methodAdapter = recipe?.let { MethodAdapter(it.method) }
        binding.rvMethod.adapter = methodAdapter

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ingredientAdapter?.updateIngredients(progress)
                binding.portionsCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initBundleData() {


        if (Build.VERSION.SDK_INT >= 33) {
            arguments.let {
                recipe = requireArguments().getParcelable(Constants.ARG_RECIPE, Recipe::class.java)
            }
        } else {
            arguments.let {
                recipe = requireArguments().getParcelable(Constants.ARG_RECIPE)
            }
        }
    }

    private fun initUI() {
        binding.tvRecipeSubTitle.text = recipe?.title

        val recipe = recipe?.id?.let { STUB.getRecipeById(recipeId = it) }
        ivRecipeItemImage = recipe?.imageUrl

        val inputStream: InputStream? =
            ivRecipeItemImage?.let { view?.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipeItemImage.setImageDrawable(drawable)

        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.devider, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }

        binding.ivFavoritesIcon.setOnClickListener{
            binding.ivFavoritesIcon.setImageResource(R.drawable.ic_heart)
        }

        binding.ivFavoritesIcon.setImageResource(R.drawable.ic_heart_empty)
        binding.rvIngredients.addItemDecoration(dividerItemDecoration)
        binding.rvMethod.addItemDecoration(dividerItemDecoration)
    }
}