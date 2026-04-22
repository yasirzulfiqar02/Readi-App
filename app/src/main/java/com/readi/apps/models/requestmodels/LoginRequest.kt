package com.readi.apps.models.requestmodels

data class LoginRequest(
    val email: String,
    val password: String,
    val device: String,
    val device_id: String,
    val fcm_token: String,
    val timezone: String
)