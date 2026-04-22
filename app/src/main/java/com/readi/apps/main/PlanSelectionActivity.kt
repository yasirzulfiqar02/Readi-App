package com.readi.apps.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.readi.apps.adapters.PlanSectionAdapter
import com.readi.apps.databinding.ActivityPlanSelectionBinding
import com.readi.apps.helper.BaseActivity
import com.readi.apps.helper.SessionManager

class PlanSelectionActivity : BaseActivity() {
    private lateinit var binding: ActivityPlanSelectionBinding
    private lateinit var adapter: PlanSectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlanSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
    }
    private fun setupClickListeners() {
        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            applyNextTransition()
        }
    }
    private fun setupRecyclerView() {
        adapter = PlanSectionAdapter()
        binding.rvPlanSection.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvPlanSection.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvPlanSection)
    }

    override fun onStop() {
        super.onStop()
        SessionManager(this).setLastActivity(this::class.java.simpleName)
    }
}