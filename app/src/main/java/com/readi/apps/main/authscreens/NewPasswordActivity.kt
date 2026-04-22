package com.readi.apps.main.authscreens

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.lifecycle.ViewModelProvider
import com.readi.apps.R
import com.readi.apps.databinding.ActivityNewPasswordBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.viewmodel.AuthViewModel

class NewPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    private lateinit var viewModel: AuthViewModel
    private var isPasswordVisible = false
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        email = intent.getStringExtra("email") ?: ""

        setupClickListeners()
        setupPasswordToggle()
        observeData()
        observeLoading(viewModel.loading)

    }
    @Suppress("DEPRECATION")
    private fun setupClickListeners() {

        binding.tvSignIN.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            applyBackTransition()
        }

        binding.btnResetPassword.setOnClickListener {

            val newPassword = binding.etNewPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (newPassword.isEmpty()) {
                CustomToastError.show(this, "New password is required")
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                CustomToastError.show(this, "New password must be at least 6 characters")
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                CustomToastError.show(this, "Confirm password is required")
                return@setOnClickListener
            }

            if (confirmPassword.length < 6) {
                CustomToastError.show(this, "Confirm password must be at least 6 characters")
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                CustomToastError.show(this, "Passwords do not match")
                return@setOnClickListener
            }

            //  API CALL
            viewModel.resetPassword(email, newPassword)
        }
    }
    private fun setupPasswordToggle() {

        binding.ivToggleNewPassword.setOnClickListener {

            val editText = binding.etNewPassword
            val cursorPosition = editText.selectionStart

            if (isPasswordVisible) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivToggleNewPassword.setImageResource(R.drawable.ic_eye_off)
            } else {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivToggleNewPassword.setImageResource(R.drawable.ic_eye_open)
            }
            editText.setSelection(cursorPosition)

            isPasswordVisible = !isPasswordVisible
        }

        binding.etNewPassword.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                if (source.contains(" ")) {
                    "" /** block space */
                } else {
                    source
                }
            }
        )

        binding.ivToggleConfirmPassword.setOnClickListener {

            val editText = binding.etConfirmPassword
            val cursorPosition = editText.selectionStart

            if (isPasswordVisible) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_off)
            } else {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_open)
            }

            //  restore cursor without modifying text
            editText.setSelection(cursorPosition)

            isPasswordVisible = !isPasswordVisible
        }

        binding.etConfirmPassword.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                if (source.contains(" ")) {
                    "" // block space
                } else {
                    source
                }
            }
        )
    }
    @Suppress("DEPRECATION")
    private fun observeData() {

        viewModel.response.observe(this) { res ->

            val message = when {
                !res.errors.isNullOrEmpty() -> res.errors.first().message
                !res.title.isNullOrEmpty() -> res.title
                !res.action.isNullOrEmpty() -> res.action
                else -> "Something went wrong"
            }

            if (res.status) {

                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("success_message", message)

                startActivity(intent)
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right)

            } else {

                CustomToastError.show(this, message)
            }
        }
    }
    @Suppress("DEPRECATION")
    private fun goToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
        applyBackTransition()
    }

    override fun handleBackPress() {
        goToSignIn()
    }
}