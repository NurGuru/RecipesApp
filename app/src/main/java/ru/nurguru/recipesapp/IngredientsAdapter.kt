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
    private var quantity: Int = 1

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
        val count = (dataSet[position].quantity)
        if ((count.toDouble() * quantity) % 1.0 != 0.0) {
            viewHolder.tvIngredientsQuantity.text =
                "${count.toDouble() * quantity}"
        } else  {
            viewHolder.tvIngredientsQuantity.text =
                "${(count.toDouble() * quantity).toInt() }"
        }

        viewHolder.tvIngredientsDescription.text = dataSet[position].description
        viewHolder.tvIngredientsUnitOfMeasure.text = dataSet[position].unitOfMeasure

    }


    override fun getItemCount() = dataSet.size


    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

}