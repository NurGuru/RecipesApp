package ru.nurguru.recipesapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ItemCategoryBinding
import ru.nurguru.recipesapp.model.Category

class CategoriesListAdapter(
    var dataSet: List<Category>,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder( val binding:ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
        val binding = ItemCategoryBinding.inflate(view, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.binding) {
            tvCategoryName.text = dataSet[position].title
            tvCategoryDescription.text = dataSet[position].description
            cvCategoryItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position])

            }

        }
        with(viewHolder.binding.ivCategoryImage){
            Glide.with(context)
                .load(dataSet[position].imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(this)
        }
    }


    override fun getItemCount() = dataSet.size

}
