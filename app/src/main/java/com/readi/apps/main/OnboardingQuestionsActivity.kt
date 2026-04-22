package com.readi.apps.main

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.readi.apps.R
import com.readi.apps.adapters.OptionsAdapter
import com.readi.apps.controllers.FlowController
import com.readi.apps.data.QuestionRepository
import com.readi.apps.databinding.ActivityOnboardingQuestionsBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.CustomToastError
import com.readi.apps.helper.SessionManager
import com.readi.apps.models.QuestionModel
import com.readi.apps.models.requestmodels.OnboardingRequest
import com.readi.apps.viewmodel.AuthViewModel
class OnboardingQuestionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingQuestionsBinding
    private val viewModel = AuthViewModel()
    private lateinit var adapter: OptionsAdapter
    private lateinit var flow: FlowController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFlow()
        setupClickListener()
        observeOnboarding()

        renderCurrent()
    }
    private fun setupFlow() {
        flow = FlowController(QuestionRepository.getAllQuestions())
    }
    private fun setupRecyclerView(){
        adapter = OptionsAdapter { selected ->
            onOptionSelected(selected)
        }

        binding.questionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.questionsRecyclerView.adapter = adapter
    }
    private fun setupClickListener() {

        binding.btnNext.setOnClickListener {
            handleNext()
        }

        binding.btnBack.setOnClickListener {
            handleBack()
        }

        binding.btnStartTranning.setOnClickListener {
            startTraining()
        }
    }
    private fun handleNext() {
        val selected = adapter.getCurrentSelectedValue() ?: return

        val next = flow.next(selected) ?: return

        render(next)
    }
    private fun handleBack() {
        val prev = flow.back() ?: return
        render(prev)
    }
    private fun onOptionSelected(selected: String) {

        updateNextButtonState(true)

        val currentId = flow.getCurrent()?.id ?: return

        flow.saveAnswer(currentId, selected)

        if (flow.isLastQuestion()) {
            updateStartButtonState(true)
        }


        when (currentId) {

            "background" -> {
                val step = flow.getStep()
                val total = getBackgroundTotal(selected)

                updateQuestionNumberPreview(step, total)

                val progress = (step * 100) / total
                animateProgress(progress)
            }

            "military_q2" -> {
                val total = if (selected == "Marine Corps") 6 else 5
                val step = flow.getStep()

                updateQuestionNumberPreview(step, total)
                val progress = (step * 100) / total
                animateProgress(progress)
            }
        }
    }
    private fun renderCurrent() {
        flow.getCurrent()?.let { render(it) }
    }
    @SuppressLint("SetTextI18n")
    private fun render(question: QuestionModel) {

        bindQuestionData(question)

        val isFirstQuestionOnly = flow.getStep() == 1

        /** BACK BUTTON VISIBILITY CONTROL */
        if (isFirstQuestionOnly) {
            binding.btnBack.visibility = View.GONE
        } else {
            binding.btnBack.visibility = View.VISIBLE
        }

        val isFirstQuestion =
            flow.getStep() == 1 && flow.getSelected("background") == null

        if (isFirstQuestion) {
            binding.tvQuestionNumber.text = "Question 1 of 1"
            animateProgress(100)
        } else {
            updateQuestionNumber()
            animateProgress(flow.getProgress())
        }

        val selectedValue = getSelectedValue(question)

        updateNextButtonState(!selectedValue.isNullOrEmpty())

        adapter.submitData(question.options, selectedValue)

        if (flow.isLastQuestion()) {

            binding.btnNext.visibility = View.GONE
            binding.btnStartTranning.visibility = View.VISIBLE
            updateStartButtonState(false)

        } else {

            binding.btnNext.visibility = View.VISIBLE
            binding.btnStartTranning.visibility = View.GONE
        }
    }
    private fun bindQuestionData(question: QuestionModel) {
        binding.tvTitle.text = question.title
        binding.tvSubtitle.text = question.subtitle
    }
    @SuppressLint("SetTextI18n")
    private fun updateQuestionNumber() {
        binding.tvQuestionNumber.text =
            "Question ${flow.getStep()} of ${flow.getTotalQuestions()}"
    }
    private fun startTraining() {

        val request = OnboardingRequest(
            background = flow.getSelected("background"),

            military_branch = flow.getSelected("military_q2")
                ?: flow.getSelected("veteran_q2"),

            age_bracking = flow.getSelected("military_common_q3")
                ?: flow.getSelected("military_marine_q3")
                ?: flow.getSelected("law_q4")
                ?: flow.getSelected("fire_q4")
                ?: flow.getSelected("veteran_q3"),

            combat_specialty = flow.getSelected("m_q4"),

            equipment = flow.getSelected("m_q5")
                ?: flow.getSelected("law_q6")
                ?: flow.getSelected("fire_q6")
                ?: flow.getSelected("veteran_q6"),

            profession = flow.getSelected("first_responder"),

            agency = flow.getSelected("law_q3")
                ?: flow.getSelected("fire_q3"),

            operational_demand = flow.getSelected("law_q5")
                ?: flow.getSelected("fire_q5")
                ?: flow.getSelected("veteran_q4")
                ?: flow.getSelected("veteran_q5")
        )

        Log.d("API_REQUEST", request.toString())
        viewModel.submitOnboarding(request)

    }
    private fun observeOnboarding() {

        viewModel.onboardingResult.observe(this) { response ->

            if (response.status) {
                val token = response.data?.token ?: ""

                if (token.isNotEmpty()) {
                    getSharedPreferences("app", MODE_PRIVATE)
                        .edit {
                            putString("token", token)
                        }
                }

                startActivity(Intent(this, LoadingActivity::class.java))
                overridePendingTransition(
                    R.anim.right_to_left,
                    R.anim.left_to_right
                )
                finish()

            } else {

                CustomToastError.show(this, response.action ?: "Onboarding failed")
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateQuestionNumberPreview(step: Int, total: Int) {
        binding.tvQuestionNumber.text = "Question $step of $total"
    }
    private fun getSelectedValue(question: QuestionModel): String? {
        return if (flow.shouldRestoreSelection()) {
            flow.getSelected(question.id)
        } else {
            null
        }
    }
    private fun getBackgroundTotal(selected: String): Int {
        return when (selected) {
            "Military" -> 5
            "First Responder" -> 6
            "Veteran" -> 6
            else -> 5
        }
    }
    private fun animateProgress(to: Int) {
        ObjectAnimator.ofInt(
            binding.progressBarHorizontal,
            "progress",
            binding.progressBarHorizontal.progress,
            to
        ).apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            start()
        }
    }
    private fun updateNextButtonState(isEnabled: Boolean) {
        binding.btnNext.isEnabled = isEnabled
        binding.btnNext.alpha = if (isEnabled) 1f else 0.5f
    }
    private fun updateStartButtonState(isEnabled: Boolean) {
        binding.btnStartTranning.isEnabled = isEnabled
        binding.btnStartTranning.alpha = if (isEnabled) 1f else 0.5f
    }

    override fun handleBackPress() {

        if (flow.getStep() <= 1) {
            finishAffinity()
            return
        }
        flow.back()
        renderCurrent()
    }

    override fun onStop() {
        super.onStop()
        SessionManager(this).setLastActivity(this::class.java.simpleName)
    }
}