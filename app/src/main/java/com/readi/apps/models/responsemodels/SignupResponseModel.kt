package com.readi.apps.models.responsemodels

import com.google.gson.annotations.SerializedName

data class RegisterResponseModel(

    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val userDataModel: Data?,
    @SerializedName("action") val action: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("errors") val errors: List<ErrorItemSignUp>? = null
)

data class ErrorItemSignUp(
    @SerializedName("field") val field: String,
    @SerializedName("message") val message: String
)

data class Data(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("is_marketing") val isMarketing: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("age") val age: String,
    @SerializedName("height") val height: String,
    @SerializedName("height_unit") val heightUnit: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("weight_unit") val weightUnit: String,
    @SerializedName("physical_activity") val physicalActivity: String,
    @SerializedName("profile_picture") val profilePicture: String,
    @SerializedName("is_password_added") val isPasswordAdded: Int,
    @SerializedName("is_public_profile") val isPublicProfile: Int,
    @SerializedName("is_email_verify") val isEmailVerify: Int,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("fitness_program_name") val fitnessProgramName: String,
    @SerializedName("fitness_program_id") val fitnessProgramId: String?,
    @SerializedName("token") val token: String,
    @SerializedName("fitness_program") val fitnessProgram: Any?,
    @SerializedName("latest_onboarding") val latestOnboarding: Any? = null)