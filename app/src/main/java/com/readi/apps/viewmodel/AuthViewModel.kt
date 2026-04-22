package com.readi.apps.viewmodel

import LoginResponseModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readi.apps.models.requestmodels.LoginRequest
import com.readi.apps.models.requestmodels.OnboardingRequest
import com.readi.apps.models.requestmodels.SignupRequest
import com.readi.apps.models.requestmodels.SocialLoginRequest
import com.readi.apps.models.responsemodels.OnboardingResponseModel
import com.readi.apps.models.responsemodels.RegisterResponseModel
import com.readi.apps.models.responsemodels.ResponseModel
import com.readi.apps.network.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    val response = MutableLiveData<ResponseModel>()

    val loading = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<RegisterResponseModel>()
    val loginResult = MutableLiveData<LoginResponseModel>()
    val socialLoginResult = MutableLiveData<LoginResponseModel>()
    val onboardingResult = MutableLiveData<OnboardingResponseModel>()
    val error = MutableLiveData<String>()

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val res = repository.forgotPassword(email)
                response.value = res
            } catch (e: Exception) {
                response.value = ResponseModel(false, e.message ?: "Error")
            }
            loading.value = false
        }
    }
    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val res = repository.verifyOtp(email, otp)
                response.value = res
            } catch (e: Exception) {
                response.value = ResponseModel(false, e.message ?: "Error")
            }
            loading.value = false
        }
    }
    fun resetPassword(email: String, password: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val res = repository.resetPassword(email, password)
                response.value = res
            } catch (e: Exception) {
                response.value = ResponseModel(false, e.message ?: "Error")
            }
            loading.value = false
        }
    }
    fun register(request: SignupRequest) {

        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.register(request)
                registerResult.postValue(response)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            loading.value = false
        }
    }
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.login(request)
                loginResult.postValue(response)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            loading.value = false
        }
    }
    fun socialLogin(request: SocialLoginRequest) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.socialLogin(request)
                socialLoginResult.postValue(response)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            loading.value = false
        }
    }
    fun submitOnboarding(request: OnboardingRequest) {
        viewModelScope.launch {
            try {
                val response = repository.submitOnboarding(request)
                onboardingResult.postValue(response)
                Log.d("API_DEBUG", response.toString())
            } catch (e: Exception) {
                error.postValue(e.message)
                    Log.e("API_ERROR", e.message.toString())

            }
        }

        fun sendFcmToken(token: String) {
            viewModelScope.launch {
                try {
                    repository.sendFcmToken(token)
                } catch (e: Exception) {
                    Log.e("FCM_TOKEN", "API Error: ${e.message}")
                }
            }
        }
    }
}
