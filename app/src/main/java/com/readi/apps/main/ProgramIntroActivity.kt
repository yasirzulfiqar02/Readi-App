package com.readi.apps.main

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.readi.apps.R
import com.readi.apps.databinding.ActivityProgramIntroBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.SessionManager

class   ProgramIntroActivity : BaseActivity() {
    private lateinit var binding: ActivityProgramIntroBinding
    private var player: ExoPlayer? = null
    private var playbackPosition: Long = 0
    private var playWhenReady: Boolean = true
    private var mediaItem: MediaItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgramIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBottomInset(findViewById(R.id.main))

        val videoUri = ("android.resource://" + packageName + "/" + R.raw.sample).toUri()
        mediaItem = MediaItem.fromUri(videoUri)

        setupVideoPlayer()
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

        SessionManager(this).setLastActivity(this::class.java.simpleName)

    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
    private fun releasePlayer() {
        player?.release()
        player = null
    }
    private fun setupClickListeners(){

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, PlanSelectionActivity::class.java))
            applyNextTransition()
        }
    }
    override fun handleBackPress() {
        finishAffinity()
    }

}