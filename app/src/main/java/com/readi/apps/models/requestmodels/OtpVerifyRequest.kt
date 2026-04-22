package com.readi.apps.models.requestmodels

data class OtpVerifyRequest(
    val email: String,
    val otp: String
)