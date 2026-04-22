package com.readi.apps.main

import android.content.Intent
import android.os.Bundle
import com.readi.apps.R
import com.readi.apps.databinding.ActivityLoadingBinding
import com.readi.apps.helper.BaseActivity

class LoadingActivity : BaseActivity() {
    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupProgressBar()
    }
    private fun setupProgressBar() {

        Thread {
            for (i in 0..100) {
                Thread.sleep(30)

                runOnUiThread {
                    binding.progressBarHorizontal.progress = i

                    if (i == 100) {
                        startActivity(Intent(this@LoadingActivity, ProgramIntroActivity::class.java))
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right)
                        finish()
                    }
                }
            }
        }.start()
    }
}