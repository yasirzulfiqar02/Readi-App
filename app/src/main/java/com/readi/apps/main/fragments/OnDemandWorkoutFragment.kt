package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.readi.apps.databinding.FragmentOnDemandWorkoutBinding
import com.readi.apps.helper.BaseFragment

class OnDemandWorkoutFragment : BaseFragment() {
    private lateinit var binding: FragmentOnDemandWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnDemandWorkoutBinding.inflate(inflater, container, false)
        setupEdgeToEdge(binding.actionBar)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}