package ru.nurguru.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.models.Category
import java.io.IOException
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        itemClickListener=listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cvCategoryItem: CardView
        val tvCategoryName: TextView
        val tvCategoryDescription: TextView
        val ivCategoryImage: ImageView

        init {
            cvCategoryItem = view.findViewById(R.id.cvCategoryItem)
            tvCategoryName = view.findViewById((R.id.tvCategoryName))
            tvCategoryDescription = view.findViewById(R.id.tvCategoryDescription)
            ivCategoryImage = view.findViewById(R.id.ivCategoryImage)
        }
    }



override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.item_category, viewGroup, false)
    return ViewHolder(view)
}

override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    viewHolder.tvCategoryName.text = dataSet[position].title
    viewHolder.tvCategoryDescription.text = dataSet[position].description

    viewHolder.cvCategoryItem.setOnClickListener {
      itemClickListener?.onItemClick()

    }
    try {
        val inputStream: InputStream? =
            fragment.context?.assets?.open(dataSet[position].imageUrl)
        val drawable = Drawable.createFromStream(inputStream, null)
        viewHolder.ivCategoryImage.setImageDrawable(drawable)
    } catch (e: IOException) {
        Log.e("error", "Ошибка при загрузке изображения", e)
        viewHolder.ivCategoryImage.contentDescription =
            "${R.string.content_description_categories_cards} ${dataSet[position].title}"
    }


}


override fun getItemCount() = dataSet.size

}
