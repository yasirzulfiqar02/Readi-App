package com.readi.apps.main.splashandonboardingscreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.readi.apps.databinding.ActivitySplashBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.SessionManager
import com.readi.apps.main.OnboardingQuestionsActivity
import com.readi.apps.main.PlanSelectionActivity
import com.readi.apps.main.ProgramIntroActivity
import com.readi.apps.main.ReviewsActivity
import com.readi.apps.main.authscreens.AddDetailsActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFCMToken { token ->
            Log.d("FCM_TOKEN", "Received in Activity: $token")
        }

        setupSplashAnimation()
        navigateToLastScreen()
    }
    @Suppress("DEPRECATION")
    private fun setupSplashAnimation() {

        binding.loadingAnimationView.visibility = View.GONE

        /** handles logo animation */
        binding.icLogo.postDelayed({
            binding.icLogo.animate()
                .y(300f)
                .setDuration(1400)
                .setInterpolator(DecelerateInterpolator())
                .start()

            /** handles loader animation */
            binding.loadingAnimationView.visibility = View.VISIBLE
            binding.loadingAnimationView.speed = 2.0f
            binding.loadingAnimationView.playAnimation()

        }, 1000)

        binding.root.postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            applyNextTransition()
            finish()
        }, 3000)
    }

    private fun navigateToLastScreen() {

        val session = SessionManager(this)
        val lastActivity = session.getLastActivity()

        if (lastActivity == null) {
            return
        }

        val intent = when (lastActivity) {

            "OnboardingQuestionsActivity" -> {
                Intent(this, OnboardingQuestionsActivity::class.java)
            }

            "PlanSelectionActivity" -> {
                Intent(this, PlanSelectionActivity::class.java)
            }

            "ProgramIntroActivity" -> {
                Intent(this, ProgramIntroActivity::class.java)
            }

            "AddDetailsActivity" -> {
                Intent(this, AddDetailsActivity::class.java)
            }

            "ReviewsActivity" -> {
                Intent(this, ReviewsActivity::class.java)
            }

            else -> {
                return
            }
        }

        startActivity(intent)
        finish()
    }
}
