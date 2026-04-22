package com.readi.apps.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R

class DotsAdapter(private val count: Int) :
    RecyclerView.Adapter<DotsAdapter.DotViewHolder>() {
    var selectedPosition = 0
    inner class DotViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dot, parent, false)
        return DotViewHolder(view)
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {

        val params = holder.view.layoutParams
        val density = holder.view.context.resources.displayMetrics.density

        if (position == selectedPosition) {
            params.width = (18 * density).toInt()
            params.height = (8 * density).toInt()
            holder.view.background = ContextCompat.getDrawable(holder.view.context, R.drawable.dot_selected)

        } else {
            params.width = (8 * density).toInt()
            params.height = (8 * density).toInt()
            holder.view.background = ContextCompat.getDrawable(holder.view.context, R.drawable.dot_unselected)
        }
        holder.view.layoutParams = params
    }

    override fun getItemCount(): Int = count

    @SuppressLint("NotifyDataSetChanged")
    fun setSelected(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}
