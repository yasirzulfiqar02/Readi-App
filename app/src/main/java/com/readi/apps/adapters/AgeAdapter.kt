package com.readi.apps.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.graphics.toColorInt
import com.readi.apps.R

class AgeAdapter(
    private val ages: List<Int>
) : RecyclerView.Adapter<AgeAdapter.ViewHolder>() {
    var selectedPosition = 0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAge: TextView = view.findViewById(R.id.tvAge)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_age, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvAge.text = ages[position].toString()

        if (position == selectedPosition) {
            holder.tvAge.setTextColor("#A8B84A".toColorInt())
            holder.tvAge.textSize = 18f
        } else {
            holder.tvAge.setTextColor(Color.WHITE)
            holder.tvAge.textSize = 15f
        }
    }

    override fun getItemCount() = ages.size
}