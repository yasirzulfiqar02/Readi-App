package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.readi.apps.R
import com.readi.apps.adapters.recovery.RecoverySubCategoryAdapter
import com.readi.apps.databinding.FragmentRecoverySubCategoryBinding
import com.readi.apps.helper.BaseFragment

class RecoverySubCategoryFragment : BaseFragment(), RecoverySubCategoryAdapter.OnCategoryClick {

    private lateinit var binding: FragmentRecoverySubCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoverySubCategoryBinding.inflate(inflater, container, false)
        setupEdgeToEdge(binding.actionBar)

        setupRecoverySubCategoryRecyclerView()
        setupClickListeners()

        return binding.root
    }
    private fun setupRecoverySubCategoryRecyclerView() {
        binding.rvRecoverySubCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRecoverySubCategory.adapter = RecoverySubCategoryAdapter(this)
    }
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSearch.setOnClickListener {
            binding.rlSearch.visibility = View.VISIBLE
        }
        binding.tvCancel.setOnClickListener {
            binding.rlSearch.visibility = View.GONE
        }
    }

    override fun onCategoryClick(position: Int) {

        val bundle = Bundle()
        bundle.putInt("position", position)

        findNavController().navigate(
            R.id.action_recoverySubCategoryFragment_to_introLetsStartFragment,
            bundle
        )
    }
}