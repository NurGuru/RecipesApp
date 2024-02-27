package ru.nurguru.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nurguru.recipesapp.databinding.ItemIngredientBinding
import ru.nurguru.recipesapp.models.Ingredient

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
private val binding = ItemIngredientBinding.bind(view)
        val tvIngredientsUnitOfMeasure = binding.tvIngredientsUnitOfMeasure
        val tvIngredientsDescription = binding.tvIngredientsDescription
        val tvIngredientsQuantity = binding.tvIngredientsQuantity
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       viewHolder.tvIngredientsDescription.text = dataSet[position].description
       viewHolder.tvIngredientsQuantity.text = dataSet[position].quantity
       viewHolder.tvIngredientsUnitOfMeasure.text = dataSet[position].unitOfMeasure
    }

    override fun getItemCount() = dataSet.size

}