package com.readi.apps.network

import LoginResponseModel
import com.readi.apps.models.requestmodels.FcmTokenRequest
import com.readi.apps.models.requestmodels.ForgotPasswordRequest
import com.readi.apps.models.requestmodels.LoginRequest
import com.readi.apps.models.requestmodels.OnboardingRequest
import com.readi.apps.models.requestmodels.OtpVerifyRequest
import com.readi.apps.models.requestmodels.SignupRequest
import com.readi.apps.models.requestmodels.ResetPasswordRequest
import com.readi.apps.models.requestmodels.SocialLoginRequest
import com.readi.apps.models.responsemodels.OnboardingResponseModel
import com.readi.apps.models.responsemodels.RegisterResponseModel
import com.readi.apps.models.responsemodels.ResponseModel

class AuthRepository {
    suspend fun forgotPassword(email: String) =
        RetrofitInstance.api.forgotPassword(ForgotPasswordRequest(email))
    suspend fun verifyOtp(email: String, otp: String) =
        RetrofitInstance.api.verifyOtp(OtpVerifyRequest(email, otp))
    suspend fun resetPassword(email: String, password: String) =
        RetrofitInstance.api.resetPassword(ResetPasswordRequest(email, password))
    suspend fun register(request: SignupRequest): RegisterResponseModel {
        return RetrofitInstance.api.register(request)
    }
    suspend fun login(request: LoginRequest): LoginResponseModel {
        return RetrofitInstance.api.login(request)
    }
    suspend fun socialLogin(request: SocialLoginRequest): LoginResponseModel {
        return RetrofitInstance.api.socialLogin(request)
    }
    suspend fun submitOnboarding(request: OnboardingRequest): OnboardingResponseModel {
        return RetrofitInstance.api.submitOnboarding(request)
    }
    suspend fun sendFcmToken(token: String) {
        RetrofitInstance.api.sendFcmToken(FcmTokenRequest(token))
    }
}