package com.readi.apps.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.readi.apps.R
import com.readi.apps.adapters.AgeAdapter
import com.readi.apps.databinding.BottomSheetAgePickerBinding
import com.readi.apps.databinding.BottomSheetGenderPickerBinding
import com.readi.apps.databinding.BottomSheetHeightPickerBinding
import com.readi.apps.databinding.BottomSheetPhysicalActivitiesBinding
import com.readi.apps.databinding.BottomSheetWeightPickerBinding
import com.readi.apps.databinding.FragmentEditPersonalDetailsBinding
import com.readi.apps.helper.BaseFragment
import com.readi.apps.helper.setCustomFont

class EditPersonalDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentEditPersonalDetailsBinding
    private var selectedActivityIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPersonalDetailsBinding.inflate(inflater, container, false)

        setupEdgeToEdge(binding.actionBar)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {

        binding.btnSelectGender.setOnClickListener {
            openGenderBottomSheet()
        }
        binding.btnSelectAge.setOnClickListener {
            openAgeBottomSheet()
        }

        binding.btnSelectHeight.setOnClickListener {
            openHeightBottomSheet()
        }
        binding.btnSelectWeight.setOnClickListener {
            openWeightBottomSheet()
        }
        binding.btnSelectPhysicalActivity.setOnClickListener {
            openPhysicalActivityBottomSheet()
        }

    }
    private fun openGenderBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetGenderPickerBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        val genders = arrayOf("Male", "Female", "Other", "Prefer not to say")

        sheetBinding.npGender.minValue = 0
        sheetBinding.npGender.maxValue = genders.size - 1
        sheetBinding.npGender.displayedValues = genders
        sheetBinding.npGender.value = 1

        sheetBinding.npGender.setCustomFont(sheetBinding.root.context, R.font.outfit_regular)

        applyPerfectHaptic(sheetBinding.npGender)

        sheetBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        sheetBinding.btnDone.setOnClickListener {
            val selectedGender = genders[sheetBinding.npGender.value]
            binding.tvSelectGender.text = selectedGender
            dialog.dismiss()
        }

        dialog.show()
    }
    @SuppressLint("SetTextI18n")
    private fun openAgeBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetAgePickerBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        val ages = (18..80).toList()

        val adapter = AgeAdapter(ages)
        val layoutManager = LinearLayoutManager(requireContext())

        sheetBinding.recyclerView.layoutManager = layoutManager
        sheetBinding.recyclerView.adapter = adapter
        sheetBinding.recyclerView.setHasFixedSize(true)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(sheetBinding.recyclerView)

        val defaultPosition = ages.indexOf(27)
        adapter.selectedPosition = defaultPosition

        sheetBinding.recyclerView.scrollToPosition(defaultPosition)

        sheetBinding.recyclerView.post {
            layoutManager.scrollToPositionWithOffset(defaultPosition, 0)
        }

        sheetBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var lastHapticTime = 0L

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    val now = System.currentTimeMillis()

                    if (now - lastHapticTime > 35) {
                        lastHapticTime = now

                        recyclerView.performHapticFeedback(
                            HapticFeedbackConstants.CLOCK_TICK
                        )
                    }
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    if (centerView != null) {
                        val position = layoutManager.getPosition(centerView)
                        if (adapter.selectedPosition != position) {
                            adapter.selectedPosition = position
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
        sheetBinding.btnDone.setOnClickListener {

            val age = ages[adapter.selectedPosition]
            binding.tvSelectAge.text = "$age years"

            dialog.dismiss()
        }

        sheetBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    @SuppressLint("SetTextI18n")
    private fun openHeightBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetHeightPickerBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        sheetBinding.npUnits.displayedValues = arrayOf("ft, inch", "cm")

        sheetBinding.npFeet.setCustomFont(requireContext(), R.font.outfit_regular)
        sheetBinding.npInch.setCustomFont(requireContext(), R.font.outfit_regular)
        sheetBinding.npCm.setCustomFont(requireContext(), R.font.outfit_regular)
        sheetBinding.npUnits.setCustomFont(requireContext(), R.font.outfit_regular)

        applyPerfectHaptic(sheetBinding.npUnits)
        applyPerfectHaptic(sheetBinding.npFeet)
        applyPerfectHaptic(sheetBinding.npInch)
        applyPerfectHaptic(sheetBinding.npCm)

        sheetBinding.npUnits.setOnValueChangedListener { _, _, newVal ->

            if (newVal == 0) {

                val cm = sheetBinding.npCm.value.toDouble()

                val totalInches = cm / 2.54
                val feet = (totalInches / 12).toInt()
                val inch = (totalInches % 12).toInt()

                sheetBinding.npFeet.value = feet
                sheetBinding.npInch.value = inch

                sheetBinding.feetGroup.visibility = View.VISIBLE
                sheetBinding.npCm.visibility = View.GONE

            } else {

                val feet = sheetBinding.npFeet.value
                val inch = sheetBinding.npInch.value

                val cm = (feet * 30.48) + (inch * 2.54)

                sheetBinding.npCm.value = cm.toInt()

                sheetBinding.feetGroup.visibility = View.GONE
                sheetBinding.npCm.visibility = View.VISIBLE
            }
        }

        sheetBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        sheetBinding.btnSave.setOnClickListener {

            if (sheetBinding.npUnits.value == 0) {
                val feet = sheetBinding.npFeet.value
                val inch = sheetBinding.npInch.value
                binding.tvSelectHeight.text = "$feet' $inch\""
                binding.tvHeightUnits.text = "ft, inch"
            } else {
                val cm = sheetBinding.npCm.value
                binding.tvSelectHeight.text = "$cm"
                binding.tvHeightUnits.text = "cm"
            }
            dialog.dismiss()
        }

        dialog.show()
    }
    @SuppressLint("DefaultLocale")
    private fun openWeightBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetWeightPickerBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(bottomSheetBinding.root)

        with(bottomSheetBinding) {

            npWeight.setFormatter { String.format("%d", it) }
            npPoints.setFormatter { String.format("%d", it) }

            bottomSheetBinding.npUnits.setCustomFont(bottomSheetBinding.root.context, R.font.outfit_regular)
            bottomSheetBinding.npWeight.setCustomFont(bottomSheetBinding.root.context, R.font.outfit_regular)
            bottomSheetBinding.npPoints.setCustomFont(bottomSheetBinding.root.context, R.font.outfit_regular)

            applyPerfectHaptic(bottomSheetBinding.npUnits)
            applyPerfectHaptic(bottomSheetBinding.npWeight)
            applyPerfectHaptic(bottomSheetBinding.npPoints)

            npUnits.displayedValues = arrayOf("Kgs", "Lbs")
            npUnits.value = 0

            var isKg = true

            npUnits.setOnValueChangedListener { _, _, newVal ->

                val weight = npWeight.value
                val points = npPoints.value

                val currentWeight = "$weight.$points".toDouble()

                if (newVal == 1 && isKg) {
                    val lbs = currentWeight / 0.45359237

                    val formatted = String.format("%.1f", lbs)
                    val parts = formatted.split(".")

                    npWeight.value = parts[0].toInt()
                    npPoints.value = parts[1].toInt()

                    isKg = false

                } else if (newVal == 0 && !isKg) {
                    val kg = currentWeight * 0.45359237

                    val formatted = String.format("%.1f", kg)
                    val parts = formatted.split(".")

                    npWeight.value = parts[0].toInt()
                    npPoints.value = parts[1].toInt()

                    isKg = true
                }
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                val weight = npWeight.value
                val points = npPoints.value
                val finalWeight = "$weight.$points"

                binding.tvSelectWeight.text = finalWeight

                val unit = if (npUnits.value == 0) "Kgs" else "Lbs"
                binding.tvWeightUnit.text = unit

                dialog.dismiss()
            }

            dialog.show()
        }
    }
    private fun openPhysicalActivityBottomSheet() {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding = BottomSheetPhysicalActivitiesBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        dialog.setOnShowListener { dialogInterface ->

            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {

                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }

        dialog.show()

        val activityLayouts = listOf(
            sheetBinding.rlActivity1,
            sheetBinding.rlActivity2,
            sheetBinding.rlActivity3,
            sheetBinding.rlActivity4
        )

        val activityImages = listOf(
            sheetBinding.ivSedentary,
            sheetBinding.ivLowActive,
            sheetBinding.ivActive,
            sheetBinding.ivVeryActive
        )

        val activityTitles = listOf(
            sheetBinding.tvActivityTitle1,
            sheetBinding.tvActivityTitle2,
            sheetBinding.tvActivityTitle3,
            sheetBinding.tvActivityTitle4
        )

        // Restore previous selection when bottom sheet opens
        if (selectedActivityIndex != -1) {

            activityLayouts[selectedActivityIndex]
                .setBackgroundResource(R.drawable.bg_bs_selected_activity)

            activityImages[selectedActivityIndex]
                .setImageResource(R.drawable.ic_fill_selector)

            activityTitles[selectedActivityIndex]
                .setTextColor(resources.getColor(R.color.white, null))
        }

        activityLayouts.forEachIndexed { index, layout ->

            layout.setOnClickListener {

                activityLayouts.forEach {
                    it.setBackgroundResource(R.drawable.bg_bs_unselected_activity)
                }

                activityImages.forEach {
                    it.setImageResource(R.drawable.ic_empty_selector)
                }

                activityTitles.forEach {
                    it.setTextColor(resources.getColor(R.color.yellow_green_text, null))
                }

                layout.setBackgroundResource(R.drawable.bg_bs_selected_activity)
                activityImages[index].setImageResource(R.drawable.ic_fill_selector)
                activityTitles[index].setTextColor(resources.getColor(R.color.white, null))

                binding.tvSelectPhysicalActivity.text = activityTitles[index].text.toString()

                selectedActivityIndex = index

                dialog.dismiss()
            }
        }
    }

    var lastHapticTime = 0L

    private fun applyPerfectHaptic(picker: com.shawnlin.numberpicker.NumberPicker) {
        picker.setOnValueChangedListener { _, _, _ ->

            val now = System.currentTimeMillis()

            if (now - lastHapticTime > 35) {
                picker.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                lastHapticTime = now
            }
        }
    }
}