package com.readi.apps.main.authscreens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.readi.apps.helper.setCustomFont
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.readi.apps.R
import com.readi.apps.adapters.AgeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.readi.apps.databinding.ActivityAddDetailsBinding
import com.readi.apps.databinding.BottomSheetAgePickerBinding
import com.readi.apps.databinding.BottomSheetGenderPickerBinding
import com.readi.apps.databinding.BottomSheetHeightPickerBinding
import com.readi.apps.databinding.BottomSheetPhysicalActivitiesBinding
import com.readi.apps.databinding.BottomSheetWeightPickerBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.helper.SessionManager
import com.readi.apps.main.ReviewsActivity

class AddDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityAddDetailsBinding
    private var selectedActivityIndex = -1
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            binding.profileImage.setImageURI(it)
        }
    }
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }
    @Suppress("DEPRECATION")
    private fun setupClickListeners() {

        binding.btnUpdateProfileImage.setOnClickListener {
            setupProfileBottomSheet()
        }
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
        binding.btnSave.setOnClickListener {
//
//            val gender = binding.tvSelectGender.text.toString().trim()
//            val age = binding.tvSelectAge.text.toString().trim()
//            val height = binding.tvSelectHeight.text.toString().trim()
//            val weight = binding.tvSelectWeight.text.toString().trim()
//            val physicalActivity = binding.tvSelectPhysicalActivity.text.toString().trim()
//
//            if (gender.isEmpty()) {
//                CustomToastError.show(this,"Please select your gender")
//            } else if (age.isEmpty()) {
//                CustomToastError.show(this,"Please select your age")
//            } else if (height.isEmpty()) {
//                CustomToastError.show(this,"Please select your height")
//            } else if (weight.isEmpty()) {
//                CustomToastError.show(this,"Please select your weight")
//            } else if (physicalActivity.isEmpty()) {
//                CustomToastError.show(this,"Please select your physical activity")
//            } else {
                startActivity(Intent(this, ReviewsActivity::class.java))
                applyNextTransition()
//            }
        }
    }

    override fun handleBackPress() {
        finishAffinity()
    }
    @SuppressLint("InflateParams")
    private fun setupProfileBottomSheet() {

        val sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_set_profile, null)
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentBottomSheet)
        bottomSheetDialog.setContentView(sheetView)

        val btnTakePhoto: View = sheetView.findViewById(R.id.tvTakePhoto)
        val btnChooseImage: View = sheetView.findViewById(R.id.tvChooseImage)
        val btnCancelButton: View = sheetView.findViewById(R.id.btnCancel)

        btnTakePhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {

                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

            bottomSheetDialog.dismiss()
        }
        btnChooseImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
            bottomSheetDialog.dismiss()
        }
        btnCancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->

            if (granted) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }
    private fun openGenderBottomSheet() {

        val dialog = BottomSheetDialog(this)
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

        val dialog = BottomSheetDialog(this)
        val sheetBinding = BottomSheetAgePickerBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        val ages = (18..80).toList()

        val adapter = AgeAdapter(ages)
        val layoutManager = LinearLayoutManager(this)

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

        val dialog = BottomSheetDialog(this)
        val sheetBinding = BottomSheetHeightPickerBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        sheetBinding.npUnits.displayedValues = arrayOf("ft, inch", "cm")

        sheetBinding.npFeet.setCustomFont(this, R.font.outfit_regular)
        sheetBinding.npInch.setCustomFont(this, R.font.outfit_regular)
        sheetBinding.npCm.setCustomFont(this, R.font.outfit_regular)
        sheetBinding.npUnits.setCustomFont(this, R.font.outfit_regular)

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

        val dialog = BottomSheetDialog(this)
        val bottomSheetBinding = BottomSheetWeightPickerBinding.inflate(LayoutInflater.from(this))
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

        val dialog = BottomSheetDialog(this)
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

    override fun onStop() {
        super.onStop()

        SessionManager(this).setLastActivity(this::class.java.simpleName)
    }
}