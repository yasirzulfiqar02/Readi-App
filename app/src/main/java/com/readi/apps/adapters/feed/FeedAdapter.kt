package com.readi.apps.adapters.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.databinding.ItemFeedBinding
import com.readi.apps.models.FeedModel

class FeedAdapter(
    private val items: List<FeedModel>
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FeedViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvUserName.text = item.userName
        holder.binding.tvCaption.text = item.caption
        holder.binding.tvWorkoutDate.text = "Workout Date: ${item.workoutDate}"
        holder.binding.tvLikeCount.text = item.likeCount.toString()
        holder.binding.tvCommentCount.text = item.commentCount.toString()

        // Player detach karo — Fragment playVideoAt() attach karega
        holder.binding.playerView.player = null

        holder.binding.btnLike.setOnClickListener {
            // Like logic
        }
    }

    // Fragment se player attach karo
    fun attachPlayer(holder: FeedViewHolder, exoPlayer: ExoPlayer) {
        holder.binding.playerView.player = exoPlayer
    }

    // ViewHolder by position find karo
    fun getViewHolder(recyclerView: RecyclerView, position: Int): FeedViewHolder? {
        return recyclerView.findViewHolderForAdapterPosition(position) as? FeedViewHolder
    }

    override fun onViewRecycled(holder: FeedViewHolder) {
        super.onViewRecycled(holder)
        // Sirf detach karo — release mat karo (Fragment handle karega)
        holder.binding.playerView.player = null
    }

    override fun getItemCount() = items.size
}