package ru.nurguru.recipesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.nurguru.recipesapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment: Fragment(R.layout.fragment_list_categories) {
    private lateinit var binding: FragmentListCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCategoriesBinding.inflate(inflater)
        return binding.root
    }
}