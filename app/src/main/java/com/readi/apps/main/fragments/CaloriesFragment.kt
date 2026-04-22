package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.readi.apps.R
import com.readi.apps.databinding.FragmentCaloriesBinding
import com.readi.apps.helper.BaseFragment

class CaloriesFragment : BaseFragment() {
    private lateinit var binding: FragmentCaloriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCaloriesBinding.inflate(inflater, container, false)

        setupEdgeToEdge(binding.actionBar)

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_caloriesFragment_to_editPersonalDetailsFragment)
        }


        return binding.root
    }
}