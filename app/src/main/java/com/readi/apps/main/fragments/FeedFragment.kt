package com.readi.apps.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.readi.apps.adapters.feed.FeedAdapter
import com.readi.apps.databinding.FragmentFeedBinding
import com.readi.apps.helper.BaseFragment
import com.readi.apps.models.FeedModel

class FeedFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter: FeedAdapter
    private lateinit var feedList: List<FeedModel>
    private var exoPlayer: ExoPlayer? = null
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        setupEdgeToEdge(binding.actionBar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sirf ek baar player banao
        exoPlayer = ExoPlayer.Builder(requireContext()).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
        }

        setupFeedList()
        setupFeed()
    }

    private fun setupFeedList() {
        feedList = listOf(
            FeedModel(
                id = "1",
                videoUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
                thumbnailUrl = "",
                userName = "Matthew Alexander",
                userAvatar = "",
                caption = "Strength, Discipline, And Dedication In Every Move.",
                workoutDate = "2026-01-01",
                likeCount = 0,
                commentCount = 0
            ),
            FeedModel(
                id = "2",
                videoUrl = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8",
                thumbnailUrl = "",
                userName = "Matthew Alexander",
                userAvatar = "",
                caption = "Strength, Discipline, And Dedication In Every Move.",
                workoutDate = "2026-01-01",
                likeCount = 0,
                commentCount = 0
            ),
            FeedModel(
                id = "3",
                videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4",
                thumbnailUrl = "",
                userName = "Matthew Alexander",
                userAvatar = "",
                caption = "Strength, Discipline, And Dedication In Every Move.",
                workoutDate = "2026-01-01",
                likeCount = 0,
                commentCount = 0
            ),
            FeedModel(
                id = "4",
                videoUrl = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8",
                thumbnailUrl = "",
                userName = "Matthew Alexander",
                userAvatar = "",
                caption = "Strength, Discipline, And Dedication In Every Move.",
                workoutDate = "2026-01-01",
                likeCount = 0,
                commentCount = 0
            ),
            FeedModel(
                id = "5",
                videoUrl = "https://cdn.jwplayer.com/manifests/yp34SRmf.m3u8",
                thumbnailUrl = "",
                userName = "Matthew Alexander",
                userAvatar = "",
                caption = "Strength, Discipline, And Dedication In Every Move.",
                workoutDate = "2026-01-01",
                likeCount = 0,
                commentCount = 0
            )
        )
    }

    private fun setupFeed() {
        adapter = FeedAdapter(feedList)
        binding.videoViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.videoViewPager.adapter = adapter
        binding.videoViewPager.offscreenPageLimit = 1

        binding.videoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
                playVideoAt(position)
            }
        })

        // Pehla video play karo
        playVideoAt(0)
    }

    private fun playVideoAt(position: Int) {
        val item = feedList[position]
        val recyclerView = binding.videoViewPager.getChildAt(0) as? RecyclerView

        // Pehle purane holder se detach karo
        adapter.getViewHolder(recyclerView ?: return, currentPosition)
            ?.binding?.playerView?.player = null

        // Naye holder se attach karo
        val holder = adapter.getViewHolder(recyclerView, position)
        holder?.let { adapter.attachPlayer(it, exoPlayer!!) }

        // Video change karo
        exoPlayer?.apply {
            setMediaItem(MediaItem.fromUri(item.videoUrl))
            prepare()
        }
    }
    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
    }
}