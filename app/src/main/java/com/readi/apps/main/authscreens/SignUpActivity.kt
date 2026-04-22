package com.readi.apps.main.authscreens

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.readi.apps.R
import com.readi.apps.databinding.ActivitySignUpBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.helper.GoogleAuthManager
import com.readi.apps.models.requestmodels.SignupRequest
import com.readi.apps.models.requestmodels.SocialLoginRequest
import com.readi.apps.viewmodel.AuthViewModel
import androidx.core.content.edit
import com.readi.apps.main.splashandonboardingscreens.OnBoardingActivity

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var googleAuth: GoogleAuthManager
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        googleAuth = GoogleAuthManager(this, launcher)

        setupClickListeners()
        setupPasswordToggle()
        observeData()
        observeLoading(viewModel.loading)

    }
    @Suppress("DEPRECATION")
    private fun setupClickListeners() {

        binding.btnBack.setOnClickListener {
            goToOnboardingActivity()

        }
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            applyBackTransition()
        }

        binding.btnCreateMembership.setOnClickListener {

            val fullName = binding.editTextFullName.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (fullName.isEmpty()) {
                CustomToastError.show(this, "Full name is required")
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                CustomToastError.show(this, "Email is required")
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                CustomToastError.show(this, "Please enter a valid email address")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                CustomToastError.show(this, "Password is required")
                return@setOnClickListener
            }
            if (password.length<6) {
                CustomToastError.show(this, "Password must be at least 6 characters")
                return@setOnClickListener
            }

            /** API Call*/
            getFCMToken { token ->
                val request = SignupRequest(
                    name = fullName,
                    email = email,
                    password = password,
                    is_marketing = if (binding.customButton.isSelected) 1 else 0,
                    device = "android",
                    device_id = getAndroidDeviceId(),
                    fcm_token = token,
                    timezone = getTimezone()
                )
                viewModel.register(request)
            }
        }

        binding.customButton.isSelected = true

        binding.clickArea.setOnClickListener {
            binding.customButton.isSelected = !binding.customButton.isSelected
        }

        binding.btnGoogleSignup.setOnClickListener {
            googleAuth.signIn()
        }
    }

    @Suppress("DEPRECATION")
    private fun goToOnboardingActivity() {
        val intent = Intent(this, OnBoardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
        applyBackTransition()
    }

    override fun handleBackPress() {
        goToOnboardingActivity()
    }
    private fun setupPasswordToggle() {

        binding.ivTogglePassword.setOnClickListener {

            val editText = binding.etPassword
            val cursorPosition = editText.selectionStart

            if (isPasswordVisible) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivTogglePassword.setImageResource(R.drawable.ic_eye_off)
            } else {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivTogglePassword.setImageResource(R.drawable.ic_eye_open)
            }

            editText.setSelection(cursorPosition)

            isPasswordVisible = !isPasswordVisible
        }

        binding.etPassword.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                if (source.contains(" ")) {
                    "" /** Block Space*/
                } else {
                    source
                }
            }
        )
    }
    @Suppress("DEPRECATION")
    private fun observeData() {

        /** Register */
        viewModel.registerResult.observe(this) { response ->

            if (response.status) {

                val token = response.userDataModel?.token ?: ""
                Log.d("TOKEN", token)

                getSharedPreferences("app", MODE_PRIVATE)
                    .edit {
                        putString("token", token)
                    }

                startActivity(Intent(this, AddDetailsActivity::class.java))
                applyNextTransition()
            } else {

                val errorMessage = response.errors?.firstOrNull()?.message
                    ?: response.title
                    ?: response.action
                    ?: "Something went wrong"

                CustomToastError.show(this, errorMessage)
            }
        }

        /** Social Login*/
        viewModel.socialLoginResult.observe(this) { response ->

            if (response.status) {

                val token = response.data?.token ?: ""
                Log.d("SOCIAL_TOKEN", token)

                getSharedPreferences("app", MODE_PRIVATE)
                    .edit {
                        putString("token", token)
                    }

                startActivity(Intent(this, AddDetailsActivity::class.java))
                applyNextTransition()
            } else {

                val errorMessage = response.errors?.firstOrNull()?.message
                    ?: response.title
                    ?: response.action
                    ?: "Social login failed"

                CustomToastError.show(this, errorMessage)
            }
        }

        /** Global Error*/
        viewModel.error.observe(this) {
            CustomToastError.show(this, it ?: "Something went wrong")
        }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            googleAuth.handleResult(
                result.data,
                onSuccess = { name, email, id ->

                    Log.d("USER_DATA", "Name: $name")
                    Log.d("USER_DATA", "Email: $email")
                    Log.d("USER_DATA", "ID: $id")

                    getFCMToken { token ->
                        val request = SocialLoginRequest(
                            name = name,
                            email = email,
                            platform = "google",
                            platform_id = id,
                            device = "android",
                            device_id = getAndroidDeviceId(),
                            fcm_token = "token",
                            timezone = getTimezone()
                        )

                        viewModel.socialLogin(request)
                    }
                },
                onError = {
                    Log.e("LOGIN_ERROR", it)
                    CustomToastError.show(this, it)
                }
            )
        }
}