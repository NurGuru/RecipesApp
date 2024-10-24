package ru.nurguru.recipesapp.ui.recipes.recipesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ItemRecipeBinding
import ru.nurguru.recipesapp.model.Recipe

class RecipesListAdapter(
     var dataSet: List<Recipe>
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRecipeBinding.bind(view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.binding) {
            tvRecipeName.text = dataSet[position].title
            cvRecipeItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position])
            }
        }
        with(viewHolder.binding.ivRecipeItemImage){Glide.with(context)
            .load(dataSet[position].imageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(this)
        }
    }

    override fun getItemCount() = dataSet.size


}