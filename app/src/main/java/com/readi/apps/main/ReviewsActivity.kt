package com.readi.apps.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R
import com.readi.apps.adapters.DotsAdapter
import com.readi.apps.adapters.ReviewsAdapter
import com.readi.apps.databinding.ActivityReviewsBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.SessionManager

class ReviewsActivity : BaseActivity() {
    private lateinit var binding: ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBottomInset(findViewById(R.id.main))

        setupReviewsViewPager()
        setupClickListeners()
    }

    override fun handleBackPress() {
        finishAffinity()
    }
    private fun setupReviewsViewPager() {

        val reviewsList = listOf(
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25",
            "I used to struggle with staying consistent, but READI keeps me on track. The military-style workouts and daily challenges are intense but so rewarding. Feels like having a personal drill sergeant in my pocket!\uD83D\uDD25"
        )
        val adapter = ReviewsAdapter(reviewsList)
        binding.reviewsRecyclerView.adapter = adapter

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.reviewsRecyclerView)

        val dotsAdapter = DotsAdapter(reviewsList.size)
        binding.dotsRecyclerView.adapter = dotsAdapter
        binding.dotsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.reviewsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val snapView = snapHelper.findSnapView(rv.layoutManager)
                val position = snapView?.let { rv.getChildAdapterPosition(it) } ?: 0
                dotsAdapter.setSelected(position)
            }
        })
    }
    private fun setupClickListeners(){
        binding.btnTrainingProgram.setOnClickListener {
            startActivity(Intent(this, OnboardingQuestionsActivity::class.java))
            applyNextTransition()
        }
    }

    override fun onStop() {
        super.onStop()

        SessionManager(this).setLastActivity(this::class.java.simpleName)
    }
}