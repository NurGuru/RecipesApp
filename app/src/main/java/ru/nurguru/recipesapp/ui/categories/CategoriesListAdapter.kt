package ru.nurguru.recipesapp.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ItemCategoryBinding
import ru.nurguru.recipesapp.model.Category
import java.io.IOException
import java.io.InputStream

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
            try {
                val inputStream: InputStream? =
                    viewHolder.itemView.context.assets?.open(dataSet[position].imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                ivCategoryImage.setImageDrawable(drawable)
            } catch (e: IOException) {
                Log.e("error", "Ошибка при загрузке изображения", e)
                ivCategoryImage.contentDescription =
                    "${R.string.content_description_categories_cards} ${dataSet[position].title}"
            }
        }
    }


    override fun getItemCount() = dataSet.size

}
