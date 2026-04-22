package com.readi.apps.controllers
import com.readi.apps.models.QuestionModel
class FlowController(
    private val questions: List<QuestionModel>
) {
    var onStateChanged: (() -> Unit)? = null

    // ---------------- FLOW STATE ----------------
    private enum class FlowType {
        NONE,
        MILITARY_NORMAL,
        MILITARY_MARINE,
        FIRST_RESPONDER,
        VETERAN
    }
    private data class State(
        val id: String,
        val step: Int
    )
    private val history = mutableListOf<State>()
    private val answers = mutableMapOf<String, String>()
    private var currentId = "background"
    private var step = 1
    private var totalQuestions = 5
    private var flowType = FlowType.NONE
    private var isBackNavigation = false
    fun getCurrent(): QuestionModel? =
        questions.find { it.id == currentId }

    fun next(selected: String): QuestionModel? {

        val current = getCurrent() ?: return null

        isBackNavigation = false

        if (currentId == "background") {
            val previousBackground = answers["background"]
            if (previousBackground != null && previousBackground != selected) {
                answers.clear()
            }
        }

        answers[currentId] = selected

        handleFlowRules(current.id, selected)

        val nextId = current.nextMap[selected] ?: return null

        saveHistory()
        moveTo(nextId)

        return getCurrent()
    }
    fun back(): QuestionModel? {

        if (history.isEmpty()) return getCurrent()

        isBackNavigation = true

        val last = history.removeAt(history.lastIndex)

        currentId = last.id
        step = last.step

        applyBackFixes()

        return getCurrent()
    }
    fun getSelected(id: String): String? {
        return answers[id]
    }
    fun saveAnswer(id: String, value: String) {
        answers[id] = value
    }
    fun getStep(): Int = step
    fun getTotalQuestions(): Int = totalQuestions
    fun getProgress(): Int =
        ((step.toFloat() / totalQuestions) * 100).toInt()
    fun shouldRestoreSelection(): Boolean = isBackNavigation
    private fun handleFlowRules(questionId: String, selected: String) {

        when (questionId) {

            "background" -> handleBackgroundSelection(selected)

            "military_q2" -> handleMilitaryQ2(selected)
        }
    }
    private fun handleBackgroundSelection(selected: String) {

        flowType = when (selected) {
            "Military" -> FlowType.MILITARY_NORMAL
            "First Responder" -> FlowType.FIRST_RESPONDER
            "Veteran" -> FlowType.VETERAN
            else -> FlowType.MILITARY_NORMAL
        }

        totalQuestions = getTotalForFlow(flowType)
        step = 1
    }
    private fun handleMilitaryQ2(selected: String) {
        if (selected == "Marine Corps") {
            flowType = FlowType.MILITARY_MARINE
            totalQuestions = 6
        } else {
            flowType = FlowType.MILITARY_NORMAL
            totalQuestions = 5
        }
    }
    private fun getTotalForFlow(type: FlowType): Int {
        return when (type) {
            FlowType.MILITARY_NORMAL -> 5
            FlowType.FIRST_RESPONDER -> 6
            FlowType.VETERAN -> 6
            FlowType.MILITARY_MARINE -> 6
            FlowType.NONE -> 5
        }
    }
    private fun saveHistory() {
        history.add(State(currentId, step))
    }
    fun moveTo(nextId: String) {
        currentId = nextId
        step++
    }
    private fun applyBackFixes() {

        when (currentId) {

            "background" -> {
                val backgroundAnswer = answers["background"]
                when (backgroundAnswer) {
                    "First Responder" -> {
                        flowType = FlowType.FIRST_RESPONDER
                        totalQuestions = 6
                    }
                    "Veteran" -> {
                        flowType = FlowType.VETERAN
                        totalQuestions = 6
                    }
                    "Military" -> {
                        flowType = FlowType.MILITARY_NORMAL
                        totalQuestions = 5
                    }
                    else -> {
                        flowType = FlowType.NONE
                        totalQuestions = 5
                    }
                }
            }

            "military_q2" -> {
                val backgroundAnswer = answers["background"]
                if (backgroundAnswer == "Military") {
                    val militaryQ2Answer = answers["military_q2"]
                    if (militaryQ2Answer == "Marine Corps") {
                        flowType = FlowType.MILITARY_MARINE
                        totalQuestions = 6
                    } else {
                        flowType = FlowType.MILITARY_NORMAL
                        totalQuestions = 5
                    }
                }
            }
        }
    }
    fun isLastQuestion(): Boolean {
        return step == totalQuestions
    }
}