package com.readi.apps.models.responsemodels

import com.google.gson.annotations.SerializedName

data class OnboardingResponseModel(

    @SerializedName("status") val status: Boolean,

    @SerializedName("data") val data: OnboardingUserData?,

    @SerializedName("action") val action: String?
)

data class OnboardingUserData(
    @SerializedName("uuid") val uuid: String?,

    @SerializedName("name") val name: String?,

    @SerializedName("email") val email: String?,

    @SerializedName("is_marketing") val isMarketing: Int?,

    @SerializedName("gender") val gender: String?,

    @SerializedName("age") val age: String?,

    @SerializedName("height") val height: String?,

    @SerializedName("height_unit") val heightUnit: String?,

    @SerializedName("weight") val weight: String?,

    @SerializedName("weight_unit") val weightUnit: String?,

    @SerializedName("physical_activity") val physicalActivity: String?,

    @SerializedName("profile_picture") val profilePicture: String?,

    @SerializedName("is_password_added") val isPasswordAdded: Int?,

    @SerializedName("is_public_profile") val isPublicProfile: Int?,

    @SerializedName("is_email_verify") val isEmailVerify: Int?,

    @SerializedName("timezone") val timezone: String?,

    @SerializedName("fitness_program_name") val fitnessProgramName: String?,

    @SerializedName("fitness_program_id") val fitnessProgramId: Int?,

    @SerializedName("token") val token: String?,

    @SerializedName("subscribe") val subscribe: Boolean?,

    @SerializedName("subscription_days") val subscriptionDays: Int?,

    @SerializedName("trial_user") val trialUser: Boolean?,

    @SerializedName("fitness_program") val fitnessProgram: FitnessProgram?,

    @SerializedName("latest_onboarding") val latestOnboarding: LatestOnboarding?
)

data class FitnessProgram(
    @SerializedName("id") val id: Int?,

    @SerializedName("title") val title: String?,

    @SerializedName("image") val image: String?,

    @SerializedName("about") val about: String?,

    @SerializedName("description") val description: String?,

    @SerializedName("sorting_number") val sortingNumber: Int?
)

data class LatestOnboarding(
    @SerializedName("id") val id: Int?,

    @SerializedName("user_id") val userId: String?,

    @SerializedName("background") val background: String?,

    @SerializedName("military_branch") val militaryBranch: String?,

    @SerializedName("age_bracking") val ageBracking: String?,

    @SerializedName("combat_specialty") val combatSpecialty: String?,

    @SerializedName("equipment") val equipment: String?,

    @SerializedName("pt_assessment") val ptAssessment: String?,

    @SerializedName("profession") val profession: String?,

    @SerializedName("agency") val agency: String?,

    @SerializedName("operational_demand") val operationalDemand: String?,

    @SerializedName("department_type") val departmentType: String?,

    @SerializedName("shift_schedule") val shiftSchedule: String?,

    @SerializedName("fitness_level") val fitnessLevel: String?,

    @SerializedName("injury_history") val injuryHistory: String?,

    @SerializedName("module") val module: String?,

    @SerializedName("program_id") val programId: Int?
)