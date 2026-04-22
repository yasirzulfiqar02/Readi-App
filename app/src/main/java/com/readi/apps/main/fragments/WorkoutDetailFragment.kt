package com.readi.apps.main.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readi.apps.R
import com.readi.apps.databinding.FragmentWorkoutDetailBinding
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.findNavController
import com.readi.apps.helper.BaseFragment

class WorkoutDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentWorkoutDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        setupEdgeToEdge(binding.actionBar)

        setupClickListeners()
        setupAlertDialog()

        return binding.root
    }
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setupAlertDialog() {
        binding.btnFinish.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            val customLayout: View = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            builder.setView(customLayout)

            builder.setCancelable(true)

            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            dialog.show()
        }
    }
}