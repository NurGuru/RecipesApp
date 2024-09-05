package ru.nurguru.recipesapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.R
import ru.nurguru.recipesapp.databinding.ItemMethodBinding

class MethodAdapter(
    var dataSet: List<String>,
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMethodBinding.bind(view)
        val tvMethod = binding.tvMethod
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvMethod.text = "%d.%s".format(position + 1, dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}
