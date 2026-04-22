package com.readi.apps.models.requestmodels

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val is_marketing: Int,
    val device: String,
    val device_id: String,
    val fcm_token: String,
    val timezone: String
)