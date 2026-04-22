package com.readi.apps.models.requestmodels

data class ResetPasswordRequest(
    val email: String,
    val password: String
)