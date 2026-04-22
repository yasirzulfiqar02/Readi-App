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
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("user/auth/forgot/password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): ResponseModel

    @POST("user/auth/otp/verify")
    suspend fun verifyOtp(
        @Body request: OtpVerifyRequest
    ): ResponseModel

    @POST("user/auth/new/password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): ResponseModel

    @POST("user/auth/register")
    suspend fun register(
        @Body request: SignupRequest
    ): RegisterResponseModel

    @POST("user/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponseModel

    @POST("user/auth/social/login")
    suspend fun socialLogin(
        @Body request: SocialLoginRequest
    ): LoginResponseModel

    @POST("user/onboarding")
    suspend fun submitOnboarding(
        @Body request: OnboardingRequest
    ): OnboardingResponseModel

    @POST("save-fcm-token")
    suspend fun sendFcmToken(@Body request: FcmTokenRequest)

}