package com.readi.apps.main.splashandonboardingscreens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.readi.apps.R
import com.readi.apps.adapters.OnBoardingAdapter
import com.readi.apps.databinding.ActivityOnBoardingBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.main.authscreens.SignInActivity
import com.readi.apps.main.authscreens.SignUpActivity
import com.readi.apps.models.OnBoardingItemModel
import kotlin.math.abs

class OnBoardingActivity : BaseActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private var player: ExoPlayer? = null
    private var playbackPosition: Long = 0
    private var playWhenReady: Boolean = true
    private var mediaItem: MediaItem? = null

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBottomInset(findViewById(R.id.main))

        val videoUri = ("android.resource://" + packageName + "/" + R.raw.sample).toUri()
        mediaItem = MediaItem.fromUri(videoUri)

        setupVideoPlayer()
        setupViewPager()
        setupSwipeGesture()
        setupClickListeners()

    }
    private fun setupVideoPlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(this).build()
            binding.videoPlayer.player = player

            player?.repeatMode = Player.REPEAT_MODE_ONE

            mediaItem?.let {
                player?.setMediaItem(it)
            }
            player?.prepare()
        }
        player?.seekTo(playbackPosition)
        player?.playWhenReady = playWhenReady
    }

    override fun onStart() {
        super.onStart()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        player?.let {
            playbackPosition = it.currentPosition
            playWhenReady = it.playWhenReady
            it.pause()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
    private fun releasePlayer() {
        player?.release()
        player = null
    }
    private fun setupViewPager() {
            val items = listOf(
                OnBoardingItemModel(
                    "The Best Workouts in Readi",
                    "Workouts designed around military training principles to help you build strength, endurance, and maintain consistent fitness progress."
                ),
                OnBoardingItemModel(
                    "Train Like the Military",
                    "Structured workouts inspired by real military routines, designed to build strength, endurance, and discipline for peak physical readiness."
                ),
                OnBoardingItemModel(
                    "Built for Strength & Readiness",
                    "Military-based training programs that push your limits, improve stamina, and keep you prepared with consistent, results-driven fitness routines."
                )
            )

            val adapter = OnBoardingAdapter(items)
            binding.textViewPager.adapter = adapter

            val dotsIndicator = binding.dotsIndicator
            dotsIndicator.attachTo(binding.textViewPager)
        }
    @SuppressLint("ClickableViewAccessibility")
    private fun setupSwipeGesture() {

        var startX = 0f
        binding.swipeView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val diff = startX - endX
                    if (abs(diff) > 100) {
                        if (diff > 0) {
                            /** Swipe Left */
                            binding.textViewPager.currentItem =
                                binding.textViewPager.currentItem + 1
                        } else {
                            /** Swipe Right */
                            binding.textViewPager.currentItem =
                                binding.textViewPager.currentItem - 1
                        }
                    }
                }
            }
            true
        }
    }
    @Suppress("DEPRECATION")
    private fun setupClickListeners(){

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            applyNextTransition()
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            applyNextTransition()
        }
    }
}
