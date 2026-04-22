package com.readi.apps.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R
import com.readi.apps.adapters.fitnessadapters.FitnessCategoryAdapter
import com.readi.apps.adapters.fitnessadapters.RecommendedProgramAdapter
import com.readi.apps.databinding.FragmentFitnessBinding
import com.readi.apps.helper.BaseFragment

class FitnessFragment : BaseFragment() {
    private lateinit var binding: FragmentFitnessBinding
    private var categoryAnchorView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFitnessBinding.inflate(inflater, container, false)
        setupEdgeToEdge(binding.actionBar)

        showCategory(Category.RECOMMENDED)

        binding.btnPersonalized.setOnClickListener {
            findNavController().navigate(R.id.action_fitnessFragment_to_onDemandWorkoutFragment)
        }

        setupFitnessCategoriesRecyclerView()
        setupRecommendedProgramsRecyclerView()
        setupOnDemandFocusOptions()
        setupOnDemandEquipmentOptions()
        return binding.root
    }
    private fun setupFitnessCategoriesRecyclerView() {

        val fitnessList = listOf(
            "Recommended Program",
            "Update Recommended Program"
        )

        val adapter = FitnessCategoryAdapter(fitnessList, object : FitnessCategoryAdapter.OnItemClickListener {
            override fun onItemClick(item: String, position: Int) {

                val anchorView = binding.rvFitnessCategory.getChildAt(position)
                if (anchorView != null) {
                    showCategoriesDropdown(anchorView)
                }
            }
        })

        binding.rvFitnessCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvFitnessCategory.adapter = adapter
    }
    @SuppressLint("InflateParams")
    private fun showCategoriesDropdown(anchor: View) {

        categoryAnchorView = anchor

        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.custom_dialog_fitness_category, null)

        val popupWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.showAsDropDown(anchor, 0, 40)

        val recommendedProgram = view.findViewById<RelativeLayout>(R.id.rlRecommendedProgram)
        val allPrograms = view.findViewById<RelativeLayout>(R.id.rlAllPrograms)
        val onDemand = view.findViewById<RelativeLayout>(R.id.rlOnDemand)
        val movementLibrary = view.findViewById<RelativeLayout>(R.id.rlMovementLibrary)

        recommendedProgram?.setOnClickListener {
            showCategory(Category.RECOMMENDED)
            popupWindow.dismiss()
        }

        allPrograms?.setOnClickListener {
            showCategory(Category.ALL_PROGRAMS)
            popupWindow.dismiss()
        }

        onDemand?.setOnClickListener {
            showCategory(Category.ON_DEMAND)
            popupWindow.dismiss()
        }

        movementLibrary?.setOnClickListener {
            showCategory(Category.MOVEMENT_LIBRARY)
            popupWindow.dismiss()
        }
    }
    private enum class Category {
        RECOMMENDED, ALL_PROGRAMS, ON_DEMAND, MOVEMENT_LIBRARY
    }
    private var selectedCategory = Category.RECOMMENDED
    private fun showCategory(category: Category) {
        selectedCategory = category

        val tvFitnessCategory = categoryAnchorView?.findViewById<TextView>(R.id.tvFitnessCategory)
        tvFitnessCategory?.text = when (category) {
            Category.RECOMMENDED -> getString(R.string.text_recommended_program)
            Category.ALL_PROGRAMS -> getString(R.string.text_all_programs)
            Category.ON_DEMAND -> getString(R.string.text_on_demand)
            Category.MOVEMENT_LIBRARY -> getString(R.string.text_movement_library)
        }

        binding.viewRecommendedProgram.visibility =
            if (category == Category.RECOMMENDED) View.VISIBLE else View.GONE
        binding.viewAllPrograms.visibility =
            if (category == Category.ALL_PROGRAMS) View.VISIBLE else View.GONE
        binding.viewOnDemand.visibility =
            if (category == Category.ON_DEMAND) View.VISIBLE else View.GONE
        binding.viewMovementLibrary.visibility =
            if (category == Category.MOVEMENT_LIBRARY) View.VISIBLE else View.GONE
    }
    private fun setupRecommendedProgramsRecyclerView() {

        val titles = listOf(
            "Tactical Squats",
            "Cardio Blast",
            "Fat Loss"
        )

        val subtitles = listOf(
            "Strengthen legs and boost balance for battlefield readiness, and soldier-level stamina.",
            "Improve stamina",
            "Burn calories"
        )

        val adapter = RecommendedProgramAdapter(titles, subtitles)
        binding.viewPagerRecommendedProgram.adapter = adapter
        binding.viewPagerRecommendedProgram.offscreenPageLimit = 1

        /** attach dots indicator to view pager  */
        binding.dotsIndicatorRecommendedProgram.attachTo(binding.viewPagerRecommendedProgram)

        /** RecyclerView clipping fix  */
        val recyclerView = binding.viewPagerRecommendedProgram.getChildAt(0) as RecyclerView
        recyclerView.clipToPadding = false
        recyclerView.clipChildren = false
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        /** view pager animation */
        binding.viewPagerRecommendedProgram.setPageTransformer { page, position ->
            page.scaleY = 1 - (0.15f * kotlin.math.abs(position))
            page.alpha = 1 - (0.3f * kotlin.math.abs(position))
        }
    }
    private fun setupOnDemandFocusOptions() {
        val options = listOf(
            binding.llSpeed,
            binding.llStability,
            binding.llStrength
        )

        fun selectOption(selected: LinearLayout) {
            options.forEach { layout ->
                if (layout == selected) {
                    layout.setBackgroundResource(R.drawable.bg_focus_active)
                } else {
                    layout.setBackgroundResource(R.drawable.bg_focus_inactive)
                }
            }
        }

        binding.llSpeed.setOnClickListener { selectOption(binding.llSpeed) }
        binding.llStability.setOnClickListener { selectOption(binding.llStability) }
        binding.llStrength.setOnClickListener { selectOption(binding.llStrength) }
    }
    private fun setupOnDemandEquipmentOptions() {
        val options = listOf(
            binding.llAllEquipment,
            binding.llBand,
            binding.llSingleWeight,
            binding.llZero
        )

        fun selectOption(selected: LinearLayout) {
            options.forEach { layout ->
                layout.setBackgroundResource(
                    if (layout == selected) R.drawable.bg_equipment_active
                    else R.drawable.bg_equipment_inactive
                )
            }
        }

        options.forEach { layout ->
            layout.setOnClickListener {
                selectOption(it as LinearLayout)
            }
        }
    }
}