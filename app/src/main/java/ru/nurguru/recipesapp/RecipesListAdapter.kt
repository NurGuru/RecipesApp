package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.databinding.ItemRecipeBinding
import ru.nurguru.recipesapp.models.Recipe
import java.io.IOException
import java.io.InputStream

class RecipesListAdapter(
    private val dataSet: List<Recipe>
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            tvRecipeName.text = dataSet[position].title
            cvRecipeItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)

            }
            try {
                val inputStream: InputStream? =
                    itemView.context.assets?.open(dataSet[position].imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                ivRecipeImage.setImageDrawable(drawable)
            } catch (e: IOException) {
                Log.e("error", "Ошибка при загрузке изображения", e)
                ivRecipeImage.contentDescription =
                    "${R.string.content_description_recipes_cards} ${dataSet[position].title}"
            }
        }
    }

    override fun getItemCount() = dataSet.size


}