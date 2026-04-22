package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.readi.apps.R
import com.readi.apps.adapters.recovery.RecoveryCategoriesAdapter
import com.readi.apps.databinding.FragmentRecoveryBinding
import com.readi.apps.helper.BaseFragment

class RecoveryFragment : BaseFragment(), RecoveryCategoriesAdapter.OnCategoryClick {
    private lateinit var binding: FragmentRecoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoveryBinding.inflate(inflater, container, false)

        setupEdgeToEdge(binding.actionBar)

        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_recoveryFragment_to_ricoveryHistoryFragment)
        }

        setupCategoriesRecyclerView()

        return binding.root
    }
    private fun setupCategoriesRecyclerView() {
        binding.rvRecoveryCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvRecoveryCategories.adapter = RecoveryCategoriesAdapter(this)
    }

    override fun onCategoryClick(position: Int) {

        val bundle = Bundle()
        bundle.putInt("position", position)

        findNavController().navigate(
            R.id.action_recoveryFragment_to_recoverySubCategoryFragment,
            bundle
        )
    }
}