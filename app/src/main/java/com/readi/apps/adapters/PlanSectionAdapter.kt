package com.readi.apps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.databinding.ItemAnnualPlanSelectionBinding
import com.readi.apps.databinding.ItemMonthlyPlanSectionBinding
class PlanSectionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ANNUAL = 0
    private val VIEW_TYPE_MONTHLY = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_ANNUAL else VIEW_TYPE_MONTHLY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_ANNUAL) {
            val binding = ItemAnnualPlanSelectionBinding.inflate(inflater, parent, false)
            AnnualViewHolder(binding)
        } else {
            val binding = ItemMonthlyPlanSectionBinding.inflate(inflater, parent, false)
            MonthlyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AnnualViewHolder -> {
            }
            is MonthlyViewHolder -> {
            }
        }
    }

    override fun getItemCount(): Int = 2
    inner class AnnualViewHolder(val binding: ItemAnnualPlanSelectionBinding) :
        RecyclerView.ViewHolder(binding.root)
    inner class MonthlyViewHolder(val binding: ItemMonthlyPlanSectionBinding) :
        RecyclerView.ViewHolder(binding.root)
}