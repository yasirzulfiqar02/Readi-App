package com.readi.apps.adapters.recovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R

class RecoveryCategoriesAdapter(
    private val listener: OnCategoryClick

) : RecyclerView.Adapter<RecoveryCategoriesAdapter.ViewHolder>() {

    interface OnCategoryClick {
        fun onCategoryClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recovery_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onCategoryClick(position)
        }
    }

    override fun getItemCount(): Int = 8
}
