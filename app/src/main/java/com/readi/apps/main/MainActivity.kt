package com.readi.apps.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.readi.apps.R
import com.readi.apps.databinding.ActivityMainBinding
import com.readi.apps.helper.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.itemIconTintList = null

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.itemRippleColor = null
        bottomNav.background = null
        bottomNav.setBackgroundColor(Color.TRANSPARENT)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val bottomDestinations = setOf(
                R.id.caloriesFragment,
                R.id.recoveryFragment,
                R.id.fitnessFragment,
                R.id.feedFragment,
                R.id.moreFragment
            )

            if (destination.id !in bottomDestinations) {
                return@addOnDestinationChangedListener
            }

            binding.bottomNavigationView.menu.findItem(R.id.caloriesFragment).setIcon(R.drawable.ic_calories)
            binding.bottomNavigationView.menu.findItem(R.id.recoveryFragment).setIcon(R.drawable.ic_recovery)
            binding.bottomNavigationView.menu.findItem(R.id.fitnessFragment).setIcon(R.drawable.ic_fitness)
            binding.bottomNavigationView.menu.findItem(R.id.feedFragment).setIcon(R.drawable.ic_feed)
            binding.bottomNavigationView.menu.findItem(R.id.moreFragment).setIcon(R.drawable.ic_more)

            when (destination.id) {
                R.id.caloriesFragment -> binding.bottomNavigationView.menu.findItem(R.id.caloriesFragment)
                    .setIcon(R.drawable.ic_calories_fill)

                R.id.recoveryFragment -> binding.bottomNavigationView.menu.findItem(R.id.recoveryFragment)
                    .setIcon(R.drawable.ic_recovery_fill)

                R.id.fitnessFragment -> binding.bottomNavigationView.menu.findItem(R.id.fitnessFragment)
                    .setIcon(R.drawable.ic_fitness_fill)

                R.id.feedFragment -> binding.bottomNavigationView.menu.findItem(R.id.feedFragment)
                    .setIcon(R.drawable.ic_feed_fill)

                R.id.moreFragment -> binding.bottomNavigationView.menu.findItem(R.id.moreFragment)
                    .setIcon(R.drawable.ic_more)
            }
        }
    }
}