package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.readi.apps.adapters.recovery.RecoveryHistoryAdapter
import com.readi.apps.databinding.FragmentRicoveryHistoryBinding
import com.readi.apps.helper.BaseFragment

class RecoveryHistoryFragment : BaseFragment() {
    private lateinit var binding: FragmentRicoveryHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRicoveryHistoryBinding.inflate(inflater, container, false)

        setupEdgeToEdge(binding.actionBar)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        setupRecoveryHistoryRecyclerView()

        return binding.root
    }

    private fun setupRecoveryHistoryRecyclerView() {
        val historyList = listOf("Post-Workout Stretch", "Lower Body Mobility", "Upper Body Mobility")
        binding.rvRecoveryHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRecoveryHistory.adapter = RecoveryHistoryAdapter(historyList)
    }
}