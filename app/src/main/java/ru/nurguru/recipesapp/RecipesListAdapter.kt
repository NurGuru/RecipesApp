package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.databinding.ItemCategoryBinding
import ru.nurguru.recipesapp.databinding.ItemRecipeBinding
import ru.nurguru.recipesapp.models.Recipe
import java.io.IOException
import java.io.InputStream

class RecipesListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: RecipesListFragment

) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRecipeBinding.bind(view)
        val cvRecipeItem = binding.cvRecipeItem
        val tvRecipeName = binding.tvRecipeName
        val ivRecipeImage = binding.ivRecipeImage

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvRecipeName.text = dataSet[position].title
        viewHolder.cvRecipeItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)

        }
        try {
            val inputStream: InputStream? =
                fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivRecipeImage.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("error", "Ошибка при загрузке изображения", e)
            viewHolder.ivRecipeImage.contentDescription =
                "${R.string.content_description_recipes_cards} ${dataSet[position].title}"
        }
    }

    override fun getItemCount() = dataSet.size
}