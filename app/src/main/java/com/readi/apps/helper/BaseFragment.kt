package com.readi.apps.helper

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

abstract class BaseFragment : Fragment() {

    protected fun setupEdgeToEdge(actionBar: View?) {

        val window = requireActivity().window
        window.statusBarColor = Color.TRANSPARENT

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = false

        actionBar?.let { bar ->
            ViewCompat.setOnApplyWindowInsetsListener(bar) { v, insets ->

                val statusBarHeight =
                    insets.getInsets(WindowInsetsCompat.Type.statusBars()).top

                val params = v.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = statusBarHeight
                v.layoutParams = params

                insets
            }
        }
    }

    protected fun setLightStatusBar(isLight: Boolean) {
        val window = requireActivity().window
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = isLight
    }
}