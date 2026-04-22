package com.readi.apps.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readi.apps.R
import com.readi.apps.databinding.ItemOnboardingQuestionsBinding

class OptionsAdapter(
    private val onSelect: (String) -> Unit
) : RecyclerView.Adapter<OptionsAdapter.VH>() {
    private var options: List<String> = emptyList()
    private var selectedValue: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(newList: List<String>, selectedValue: String?) {
        options = newList
        this.selectedValue = selectedValue
        notifyDataSetChanged()
    }
    fun getCurrentSelectedValue(): String? = selectedValue

    inner class VH(
        val binding: ItemOnboardingQuestionsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemOnboardingQuestionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = options[position]

        val isSelected = item == selectedValue

        bindItem(holder.binding, item, isSelected)

        holder.binding.root.setOnClickListener {
            handleSelection(item)
        }
    }

    override fun getItemCount(): Int = options.size
    private fun bindItem(binding: ItemOnboardingQuestionsBinding, item: String, isSelected: Boolean
    ) {
        binding.tvName.text = item

        binding.root.setBackgroundResource(
            if (isSelected)
                R.drawable.bg_onboarding_questions_selected
            else
                R.drawable.bg_onboarding_questions_unselected
        )

        binding.ivSelector.setImageResource(
            if (isSelected)
                R.drawable.ic_fill_selector
            else
                R.drawable.ic_empty_selector
        )
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun handleSelection(item: String) {
        selectedValue = item
        notifyDataSetChanged()
        onSelect(item)
    }
}