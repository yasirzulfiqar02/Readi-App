package com.readi.apps.main.authscreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.readi.apps.R
import com.readi.apps.databinding.ActivitySignInBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.helper.GoogleAuthManager
import com.readi.apps.main.MainActivity
import com.readi.apps.models.requestmodels.LoginRequest
import com.readi.apps.viewmodel.AuthViewModel
import androidx.core.content.edit
import com.readi.apps.helper.CustomToastSuccess
import com.readi.apps.main.OnboardingQuestionsActivity
import com.readi.apps.main.splashandonboardingscreens.OnBoardingActivity
import com.readi.apps.models.requestmodels.SocialLoginRequest

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var googleAuth: GoogleAuthManager
    private var isPasswordVisible = false

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        googleAuth = GoogleAuthManager(this, launcher)

        intent.getStringExtra("success_message")?.let { msg ->
            CustomToastSuccess.show(this, msg)
        }

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

        binding.btnResetIt.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
            applyNextTransition()
        }

        binding.btnSignUP.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            applyNextTransition()
        }

        binding.btnSignIn.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                CustomToastError.show(this, "Email is required")

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                CustomToastError.show(this, "Please enter a valid email address")

            } else if (password.isEmpty()) {
                CustomToastError.show(this, "Password is required")

            } else if (password.length < 6) {
                CustomToastError.show(this, "Password must be at least 6 characters")

            } else {

                /** API CALL */
                getFCMToken { token ->
                    val request = LoginRequest(
                        email = email,
                        password = password,
                        device = "android",
                        device_id = getAndroidDeviceId(),
                        fcm_token = token,
                        timezone = getTimezone()
                    )
                    viewModel.login(request)
                }
            }
        }

        binding.btnSignInWithGoogle.setOnClickListener {
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
                    "" /** block space*/
                } else {
                    source
                }
            }
        )
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
    @Suppress("DEPRECATION")
    private fun observeData() {

        /** Login */
        viewModel.loginResult.observe(this) { response ->

            if (response.status) {

                val token = response.data?.token ?: ""
                Log.d("TOKEN", token)

                getSharedPreferences("app", MODE_PRIVATE)
                    .edit {
                        putString("token", token)
                    }

                /** Check latest_onboarding */
//                val latestOnboarding = response.data?.latest_onboarding
//
//                if (latestOnboarding == null) {
//                    startActivity(Intent(this, MainActivity::class.java))
//                } else {
//                    startActivity(Intent(this, OnboardingQuestionsActivity::class.java))
//                }

                startActivity(Intent(this, MainActivity::class.java))
                applyNextTransition()
                finish()

            } else {
                val errorMessage = response.errors?.firstOrNull()?.message
                    ?: response.title
                    ?: response.action
                    ?: "Invalid email or password"

                CustomToastError.show(this, errorMessage)
            }
        }

        /** SOCIAL LOGIN */
        viewModel.socialLoginResult.observe(this) { response ->

            if (response.status) {

                val token = response.data?.token ?: ""
                Log.d("SOCIAL_TOKEN", token)

                getSharedPreferences("app", MODE_PRIVATE)
                    .edit {
                        putString("token", token)
                    }

                val latestOnboarding = response.data?.latest_onboarding

                if (latestOnboarding == null) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, OnboardingQuestionsActivity::class.java))
                }

                applyNextTransition()
                finish()

            } else {

                val errorMessage = response.errors?.firstOrNull()?.message
                    ?: response.title
                    ?: response.action
                    ?: "Social login failed"

                CustomToastError.show(this, errorMessage)
            }
        }
        /** GLOBAL ERROR */
        viewModel.error.observe(this) {
            CustomToastError.show(this, it ?: "Something went wrong")
        }
    }
}