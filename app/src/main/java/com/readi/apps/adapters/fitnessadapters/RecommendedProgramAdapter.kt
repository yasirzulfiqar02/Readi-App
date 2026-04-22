package com.readi.apps.adapters.fitnessadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.databinding.ItemRecommendedProgramsBinding

class RecommendedProgramAdapter(
    private val titles: List<String>,
    private val subtitles: List<String>
) : RecyclerView.Adapter<RecommendedProgramAdapter.VH>() {

    inner class VH(val binding: ItemRecommendedProgramsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRecommendedProgramsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val title = titles[position % titles.size]
        val subtitle = subtitles[position % subtitles.size]

        holder.binding.tvTitle.text = title
        holder.binding.tvSubtitle.text = subtitle

    }

    override fun getItemCount(): Int = 5
}