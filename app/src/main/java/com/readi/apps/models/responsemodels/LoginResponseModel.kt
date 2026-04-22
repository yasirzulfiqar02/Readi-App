import com.google.gson.annotations.SerializedName
data class LoginResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: UserData?,
    @SerializedName("action") val action: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("errors") val errors: List<ErrorItemLogin>?
)

data class UserData(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("is_marketing") val is_marketing: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("age") val age: String,
    @SerializedName("height") val height: String,
    @SerializedName("height_unit") val height_unit: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("weight_unit") val weight_unit: String,
    @SerializedName("physical_activity") val physical_activity: String,
    @SerializedName("profile_picture") val profile_picture: String,
    @SerializedName("is_password_added") val is_password_added: Int,
    @SerializedName("is_public_profile") val is_public_profile: Int,
    @SerializedName("is_email_verify") val is_email_verify: Int,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("fitness_program_name") val fitness_program_name: String,
    @SerializedName("fitness_program_id") val fitness_program_id: Int,
    @SerializedName("token") val token: String,
    @SerializedName("subscribe") val subscribe: Boolean,
    @SerializedName("subscription_days") val subscription_days: Int,
    @SerializedName("trial_user") val trial_user: Boolean,
    @SerializedName("fitness_program") val fitness_program: FitnessProgram?,
    @SerializedName("latest_onboarding") val latest_onboarding: LatestOnboarding?
)

data class FitnessProgram(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("about") val about: String,
    @SerializedName("description") val description: String,
    @SerializedName("sorting_number") val sorting_number: Int,
    @SerializedName("video") val video: String,
    @SerializedName("video_thumb") val video_thumb: String,
    @SerializedName("coach_name") val coach_name: String,
    @SerializedName("detail_description") val detail_description: String
)

data class LatestOnboarding(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("background") val background: String,
    @SerializedName("military_branch") val military_branch: String,
    @SerializedName("age_bracking") val age_bracking: String,
    @SerializedName("combat_specialty") val combat_specialty: String,
    @SerializedName("equipment") val equipment: String,
    @SerializedName("pt_assessment") val pt_assessment: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("agency") val agency: String,
    @SerializedName("operational_demand") val operational_demand: String,
    @SerializedName("department_type") val department_type: String,
    @SerializedName("shift_schedule") val shift_schedule: String,
    @SerializedName("fitness_level") val fitness_level: String,
    @SerializedName("injury_history") val injury_history: String,
    @SerializedName("module") val module: String,
    @SerializedName("program_id") val program_id: Int
)

data class ErrorItemLogin(
    @SerializedName("field") val field: String,
    @SerializedName("message") val message: String
)