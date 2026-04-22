package com.readi.apps.main.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import com.readi.apps.R
import com.readi.apps.databinding.FragmentIntroLetsStartBinding

class IntroLetsStartFragment : Fragment() {
    private lateinit var binding: FragmentIntroLetsStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroLetsStartBinding.inflate(inflater, container, false)


        val window = requireActivity().window
        window.statusBarColor = Color.TRANSPARENT
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false
        val actionBar = binding.actionBar
        ViewCompat.setOnApplyWindowInsetsListener(actionBar) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = statusBarHeight
            v.layoutParams = params
            insets
        }

        binding.btnLetsStart.setOnClickListener {
            findNavController().navigate(R.id.action_introLetsStartFragment_to_workoutDetailFragment)
        }

        setupClickListeners()

        return binding.root
    }
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}