package com.readi.apps.main.authscreens

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.readi.apps.R
import com.readi.apps.databinding.ActivityEmailVerificationBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.helper.CustomToastSuccess
import com.readi.apps.viewmodel.AuthViewModel

class EmailVerificationActivity : BaseActivity() {
    private lateinit var binding: ActivityEmailVerificationBinding
    private lateinit var viewModel: AuthViewModel
    private var email: String = ""
    private var isResend = false
    private var isVerifyOtp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        email = intent.getStringExtra("email") ?: ""

        binding.userEmail.text = email

        val editTexts = arrayOf(
            binding.otp1,
            binding.otp2,
            binding.otp3,
            binding.otp4,
            binding.otp5,
            binding.otp6
        )

        setupClickListeners()
        setupOTPLogic(editTexts)
        observeData()
        observeLoading(viewModel.loading)
        autoKeyboardOpen()

    }
    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            goToForgotPasswordActivity()
        }

        binding.btnVerify.setOnClickListener {

            isVerifyOtp = true
            isResend = false

            val otp1 = binding.otp1.text.toString().trim()
            val otp2 = binding.otp2.text.toString().trim()
            val otp3 = binding.otp3.text.toString().trim()
            val otp4 = binding.otp4.text.toString().trim()
            val otp5 = binding.otp5.text.toString().trim()
            val otp6 = binding.otp6.text.toString().trim()

            val otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6

            if (otp.isEmpty()) {
                CustomToastError.show(this, "OTP is required")
                return@setOnClickListener
            }

            /** API CALL */
            viewModel.verifyOtp(email, otp)
        }

        binding.tvResendOtp.setOnClickListener {
            showResendOtpDialog()
        }

    }
    @Suppress("DEPRECATION")
    private fun goToForgotPasswordActivity() {
        val intent = Intent(this, ForgetPasswordActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
        applyBackTransition()
    }

    override fun handleBackPress() {
        goToForgotPasswordActivity()
    }

    private fun setupOTPLogic(editTexts: Array<EditText>) {
        for (i in editTexts.indices) {
            val et = editTexts[i]

            et.addTextChangedListener(object : TextWatcher {
                private var isInternalUpdate = false

                override fun afterTextChanged(s: Editable?) {
                    if (isInternalUpdate) return

                    val text = s?.toString() ?: ""

                    /** Paste Logic */
                    if (i == 0 && text.length > 1) {
                        isInternalUpdate = true
                        val otp = text.trim().take(editTexts.size)

                        for (j in editTexts.indices) {
                            if (j < otp.length) {
                                editTexts[j].setText(otp[j].toString())
                            }
                        }

                        /** Focus to last box */
                        val lastBoxIndex = if (otp.length < editTexts.size) otp.length - 1 else editTexts.size - 1
                        editTexts[lastBoxIndex].requestFocus()
                        editTexts[lastBoxIndex].setSelection(editTexts[lastBoxIndex].text.length)
                        isInternalUpdate = false
                    }
                    /** Normal next focus logic */
                    else if (text.length == 1 && i < editTexts.size - 1) {
                        editTexts[i + 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            /** Backspace handling */
            et.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {

                    if (et.text.isEmpty() && i > 0) {
                        editTexts[i - 1].requestFocus()
                        editTexts[i - 1].setText("")
                        return@setOnKeyListener true
                    }
                }
                false
            }
        }
    }
    @Suppress("DEPRECATION")
    private fun observeData() {

        viewModel.response.observe(this) { res ->

            if (res.status) {

                /** Verify Otp */
                if (isVerifyOtp) {

                    isVerifyOtp = false

                    val intent = Intent(this, NewPasswordActivity::class.java)
                    intent.putExtra("email", email)

                    startActivity(intent)
                    applyNextTransition()
                    return@observe
                }

                /** Resend Otp */
                if (isResend) {

                    val successMessage = res.errors?.firstOrNull()?.message
                        ?: res.title
                        ?: "OTP sent successfully"

                    CustomToastSuccess.show(this, successMessage)

                    clearOtpFields()

                    isResend = false
                    return@observe
                }

            } else {

                val errorMessage = when {

                    !res.errors.isNullOrEmpty() -> res.errors.first().message

                    !res.title.isNullOrEmpty() -> res.title

                    isVerifyOtp ->
                        "The OTP you entered is invalid or expired."

                    else ->
                        "Something went wrong. Please try again."
                }

                CustomToastError.show(this, errorMessage)

                isVerifyOtp = false
            }
        }
    }

    override fun onResume() {
        super.onResume()

        isResend = false
        isVerifyOtp = false

        clearOtpFields()
        binding.otp1.postDelayed({
            binding.otp1.requestFocus()
        }, 100)
    }
    private fun clearOtpFields() {

        binding.otp1.text?.clear()
        binding.otp2.text?.clear()
        binding.otp3.text?.clear()
        binding.otp4.text?.clear()
        binding.otp5.text?.clear()
        binding.otp6.text?.clear()

        binding.otp1.requestFocus()
    }
    private fun autoKeyboardOpen() {

        binding.otp1.requestFocus()

        binding.otp1.postDelayed({

            val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            imm.showSoftInput(binding.otp1, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)

        }, 200)

    }
    private fun showResendOtpDialog() {

        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_resend_otp, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnOk = dialogView.findViewById<TextView>(R.id.btnOk)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)

        btnOk.setOnClickListener {

            dialog.dismiss()

            isResend = true
            isVerifyOtp = false

            viewModel.forgotPassword(email)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
