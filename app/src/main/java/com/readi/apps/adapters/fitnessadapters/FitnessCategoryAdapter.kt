package com.readi.apps.adapters.fitnessadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R

class FitnessCategoryAdapter(
    private val recoveryList: List<String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FitnessCategoryAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: String, position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFitnessCategory: TextView = view.findViewById(R.id.tvFitnessCategory)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(recoveryList[position], position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fitness_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFitnessCategory.text = recoveryList[position]
    }

    override fun getItemCount(): Int = recoveryList.size
}
