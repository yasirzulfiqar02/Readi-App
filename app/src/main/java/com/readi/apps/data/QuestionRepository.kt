package com.readi.apps.data

import com.readi.apps.models.QuestionModel

object QuestionRepository {
    fun getAllQuestions(): List<QuestionModel> {
        return listOf(

            QuestionModel(
                id = "background",
                title = "Choose Your Background",
                subtitle = "Select the background that best represents your experience. This helps personalize your training programs.",
                options = listOf("Military", "First Responder", "Veteran"),
                nextMap = mapOf(
                    "Military" to "military_q2",
                    "First Responder" to "first_responder",
                    "Veteran" to "veteran_q2"
                )
            ),

            QuestionModel(
                id = "military_q2",
                title = "Choose Your Military Branch",
                subtitle = "Which military branch would you like your training to be inspired by?",
                options = listOf("Army", "Navy", "Air Force","Marine Corps", "Coast Guard", "Space Force"),
                nextMap = mapOf(
                    "Army" to "military_common_q3",
                    "Navy" to "military_common_q3",
                    "Air Force" to "military_common_q3",
                    "Marine Corps" to "military_marine_q3",
                    "Coast Guard" to "military_common_q3",
                    "Space Force" to "military_common_q3"

                )
            ),

            QuestionModel(
                id = "military_common_q3",
                title = "Age Bracking",
                subtitle = "Creating progress with the best challenges for your stage of development.",
                options = listOf(
                    "17-21", "22-26", "27-31", "32-36", "37-41",
                    "42-46", "47-51", "52-56", "57-61", "62+"
                ),
                nextMap = mapOf(
                    "17-21" to "m_q4",
                    "22-26" to "m_q4",
                    "27-31" to "m_q4",
                    "32-36" to "m_q4",
                    "37-41" to "m_q4",
                    "42-46" to "m_q4",
                    "47-51" to "m_q4",
                    "52-56" to "m_q4",
                    "57-61" to "m_q4",
                    "62+" to "m_q4"
                )
            ),

            QuestionModel(
                id = "military_marine_q3",
                title = "Age Bracking",
                subtitle = "Creating progress with the best challenges for your stage of development.",
                options = listOf(
                    "17-21", "22-26", "27-31", "32-36", "37-41",
                    "42-46", "47-51", "52-56", "57-61", "62+"
                ),
                nextMap = mapOf(
                    "17-21" to "marine_q4",
                    "22-26" to "marine_q4",
                    "27-31" to "marine_q4",
                    "32-36" to "marine_q4",
                    "37-41" to "marine_q4",
                    "42-46" to "marine_q4",
                    "47-51" to "marine_q4",
                    "52-56" to "marine_q4",
                    "57-61" to "marine_q4",
                    "62+" to "marine_q4"
                )
            ),

            QuestionModel(
                id = "marine_q4",
                title = "PT Assessment",
                subtitle = "Select your branch to see the relevant physical training assessment.",
                options = listOf("PFT (Physical Fitness Test)", "CFT (Combat Fitness Test)"),
                nextMap = mapOf(
                    "PFT (Physical Fitness Test)" to "m_q4",
                    "CFT (Combat Fitness Test)" to "m_q4"
                )
            ),

            QuestionModel(
                id = "m_q4",
                title = "Combat Specialty",
                subtitle = "Are you in a combat specialty/MOS (e.g., infantry, artillery, special operations)?",
                options = listOf("Yes", "No"),
                nextMap = mapOf(
                    "Yes" to "m_q5",
                    "No" to "m_q5"
                )
            ),

            QuestionModel(
                id = "m_q5",
                title = "Equipment",
                subtitle = "Choose your equipment to customize your military-style workouts.",
                options = listOf("No Equipment", "Dumbbells", "Kettlebells", "Barbell and plates", "Full Gym"),
                nextMap = mapOf(
                    "No Equipment" to "end",
                    "Dumbbells" to "end",
                    "Kettlebells" to "end",
                    "Barbell and plates" to "end",
                    "Full Gym" to "end"
                )
            ),

            QuestionModel(
                id = "first_responder",
                title = "Choose Your Profession",
                subtitle = "Select the profession that best represents your role or interest.",
                options = listOf("Law Enforcement", "Firefighter"),
                nextMap = mapOf(
                    "Law Enforcement" to "law_q3",
                    "Firefighter" to "fire_q3"
                )
            ),

            QuestionModel(
                id = "law_q3",
                title = "Type of Agency",
                subtitle = "Select the agency or unit that best represents your role.",
                options = listOf(
                    "Patrol / Deputy",
                    "SWAT / Tactical Team",
                    "Federal Agent",
                    "Corrections Officer",
                    "Detective / Investigator",
                    "Academy / Recruit"
                ),
                nextMap = mapOf(
                    "Patrol / Deputy" to "law_q4",
                    "SWAT / Tactical Team" to "law_q4",
                    "Federal Agent" to "law_q4",
                    "Corrections Officer" to "law_q4",
                    "Detective / Investigator" to "law_q4",
                    "Academy / Recruit" to "law_q4"
                )
            ),

            QuestionModel(
                id = "law_q4",
                title = "Age Bracking",
                subtitle = "Creating progress with the best challenges for your stage of development.",
                options = listOf(
                    "17-21", "22-26", "27-31", "32-36", "37-41",
                    "42-46", "47-51", "52-56", "57-61", "62+"
                ),
                nextMap = mapOf(
                    "17-21" to "law_q5",
                    "22-26" to "law_q5",
                    "27-31" to "law_q5",
                    "32-36" to "law_q5",
                    "37-41" to "law_q5",
                    "42-46" to "law_q5",
                    "47-51" to "law_q5",
                    "52-56" to "law_q5",
                    "57-61" to "law_q5",
                    "62+" to "law_q5"
                )
            ),

            QuestionModel(
                id = "law_q5",
                title = "Operational Demand",
                subtitle = "Indicate how physically demanding your role is on a daily basis.",
                options = listOf(
                    "High – Frequent foot pursuits and physical arrests.",
                    "Moderate – Mixed patrol and field duties.",
                    "Low – Mostly administrative or investigative work."
                ),
                nextMap = mapOf(
                    "High – Frequent foot pursuits and physical arrests." to "law_q6",
                    "Moderate – Mixed patrol and field duties." to "law_q6",
                    "Low – Mostly administrative or investigative work." to "law_q6"
                )
            ),

            QuestionModel(
                id = "law_q6",
                title = "Equipment",
                subtitle = "Choose your equipment to customize your military-style workouts.",
                options = listOf("No Equipment", "Dumbbells", "Kettlebells", "Barbell and plates", "Full Gym"),
                nextMap = mapOf(
                    "No Equipment" to "end",
                    "Dumbbells" to "end",
                    "Kettlebells" to "end",
                    "Barbell and plates" to "end",
                    "Full Gym" to "end"
                )
            ),

            QuestionModel(
                id = "fire_q3",
                title = "Department Type",
                subtitle = "Select the fire department role that best fits your duties to receive relevant resources and guidance.",
                options = listOf(
                    "Structural Firefighter",
                    "Wildland Firefighter",
                    "Volunteer Firefighter",
                    "Fire Officer / Command",
                    "Recruit / Academy"
                ),
                nextMap = mapOf(
                    "Structural Firefighter" to "fire_q4",
                    "Wildland Firefighter" to "fire_q4",
                    "Volunteer Firefighter" to "fire_q4",
                    "Fire Officer / Command" to "fire_q4",
                    "Recruit / Academy" to "fire_q4"
                )
            ),

            QuestionModel(
                id = "fire_q4",
                title = "Age Bracking",
                subtitle = "Creating progress with the best challenges for your stage of development.",
                options = listOf(
                    "17-21", "22-26", "27-31", "32-36", "37-41",
                    "42-46", "47-51", "52-56", "57-61", "62+"
                ),
                nextMap = mapOf(
                    "17-21" to "fire_q5",
                    "22-26" to "fire_q5",
                    "27-31" to "fire_q5",
                    "32-36" to "fire_q5",
                    "37-41" to "fire_q5",
                    "42-46" to "fire_q5",
                    "47-51" to "fire_q5",
                    "52-56" to "fire_q5",
                    "57-61" to "fire_q5",
                    "62+" to "fire_q5"
                )
            ),

            QuestionModel(
                id = "fire_q5",
                title = "What Is Your Shift Schedule?",
                subtitle = "Select the shift pattern that best reflects your current work schedule.",
                options = listOf(
                    "24 on / 48 off",
                    "24 on / 72 off",
                    "48 on / 96 off",
                    "Day shift",
                    "Volunteer / Variable"
                ),
                nextMap = mapOf(
                    "24 on / 48 off" to "fire_q6",
                    "24 on / 72 off" to "fire_q6",
                    "48 on / 96 off" to "fire_q6",
                    "Day shift" to "fire_q6",
                    "Volunteer / Variable" to "fire_q6"
                )
            ),

            QuestionModel(
                id = "fire_q6",
                title = "Equipment",
                subtitle = "Choose your equipment to customize your military-style workouts.",
                options = listOf("No Equipment", "Dumbbells", "Kettlebells", "Barbell and plates", "Full Gym"),
                nextMap = mapOf(
                    "No Equipment" to "end",
                    "Dumbbells" to "end",
                    "Kettlebells" to "end",
                    "Barbell and plates" to "end",
                    "Full Gym" to "end"
                )
            ),

            QuestionModel(
                id = "veteran_q2",
                title = "Service Background",
                subtitle = "Select the military branch you have served in or are affiliated with.",
                options = listOf("Army", "Navy", "Air Force", "Marine Corps", "Coast Guard", "Space Force"),
                nextMap = mapOf(
                    "Army" to "veteran_q3",
                    "Navy" to "veteran_q3",
                    "Air Force" to "veteran_q3",
                    "Marine Corps" to "veteran_q3",
                    "Coast Guard" to "veteran_q3",
                    "Space Force" to "veteran_q3"
                )
            ),

            QuestionModel(
                id = "veteran_q3",
                title = "Age Bracking",
                subtitle = "Creating progress with the best challenges for your stage of development.",
                options = listOf(
                    "17-21", "22-26", "27-31", "32-36", "37-41",
                    "42-46", "47-51", "52-56", "57-61", "62+"
                ),
                nextMap = mapOf(
                    "17-21" to "veteran_q4",
                    "22-26" to "veteran_q4",
                    "27-31" to "veteran_q4",
                    "32-36" to "veteran_q4",
                    "37-41" to "veteran_q4",
                    "42-46" to "veteran_q4",
                    "47-51" to "veteran_q4",
                    "52-56" to "veteran_q4",
                    "57-61" to "veteran_q4",
                    "62+" to "veteran_q4"
                )
            ),

            QuestionModel(
                id = "veteran_q4",
                title = "Current Fitness Level – Getting Back Into Training",
                subtitle = "Select the option that best describes your current activity and fitness level.",
                options = listOf("Moderately Active", "Consistently Training", "Highly Trained"),
                nextMap = mapOf(
                    "Moderately Active" to "veteran_q5",
                    "Consistently Training" to "veteran_q5",
                    "Highly Trained" to "veteran_q5"
                )
            ),

            QuestionModel(
                id = "veteran_q5",
                title = "Injury History",
                subtitle = "Select the option that best describes any past or current injuries to help tailor your training safely.",
                options = listOf("No Major Injuries", "Joint Issues", "Back Issues","Multiple Areas","Prefer Low-Impact Training"),
                nextMap = mapOf(
                    "No Major Injuries" to "veteran_q6",
                    "Joint Issues" to "veteran_q6",
                    "Back Issues" to "veteran_q6",
                    "Multiple Areas" to "veteran_q6",
                    "Prefer Low-Impact Training" to "veteran_q6"
                )
            ),

            QuestionModel(
                id = "veteran_q6",
                title = "Equipment",
                subtitle = "Choose your equipment to customize your military-style workouts.",
                options = listOf("No Equipment", "Dumbbells", "Kettlebells", "Barbell and plates", "Full Gym"),
                nextMap = mapOf(
                    "No Equipment" to "end",
                    "Dumbbells" to "end",
                    "Kettlebells" to "end",
                    "Barbell and plates" to "end",
                    "Full Gym" to "end"
                )
            ),
        )
    }
}