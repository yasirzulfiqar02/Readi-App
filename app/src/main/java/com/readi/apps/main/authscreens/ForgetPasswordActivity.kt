package com.readi.apps.main.authscreens

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.readi.apps.databinding.ActivityForgetPasswordBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.viewmodel.AuthViewModel

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupClickListeners()
        observeData()
        observeLoading(viewModel.loading)
    }
    private fun setupClickListeners() {

        binding.btnBack.setOnClickListener {
            goToSignIn()
        }

        binding.btnSendOtp.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString().trim()

            if (email.isEmpty()) {
                CustomToastError.show(this, "Email is required")
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                CustomToastError.show(this, "Please enter a valid email address")
                return@setOnClickListener
            }

            /** API Call */
            viewModel.forgotPassword(email)
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
    @Suppress("DEPRECATION")
    private fun observeData() {

        viewModel.response.observe(this) { res ->

            if (res.status) {

                val email = binding.editTextTextEmailAddress.text.toString().trim()

                val intent = Intent(this, EmailVerificationActivity::class.java)
                intent.putExtra("email", email)

                startActivity(intent)
                applyNextTransition()
            } else {

                val errorMessage = res.errors?.firstOrNull()?.message
                    ?: res.title
                    ?: "Something went wrong"

                CustomToastError.show(this, errorMessage)
            }
        }
    }

}