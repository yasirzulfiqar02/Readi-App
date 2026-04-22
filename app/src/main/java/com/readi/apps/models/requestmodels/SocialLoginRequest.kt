package com.readi.apps.models.requestmodels

data class SocialLoginRequest(
    val name: String?,
    val email: String?,
    val platform: String,
    val platform_id: String?,
    val device: String,
    val device_id: String,
    val fcm_token: String,
    val timezone: String
)