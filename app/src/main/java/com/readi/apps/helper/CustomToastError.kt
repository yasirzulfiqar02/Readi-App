package com.readi.apps.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.readi.apps.R

object CustomToastError {

    private var isShowing = false

    fun show(activity: Activity, message: String) {

        if (isShowing) return

        isShowing = true

        val parentLayout = activity.findViewById<ViewGroup>(android.R.id.content)

        val inflater = LayoutInflater.from(activity)
        val toastView = inflater.inflate(R.layout.custom_toast, parentLayout, false)

        val text = toastView.findViewById<TextView>(R.id.toastText)
        text.text = message

        val marginHorizontal = (16 * activity.resources.displayMetrics.density).toInt()
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(marginHorizontal, 0, marginHorizontal, 0)
        toastView.layoutParams = layoutParams

        parentLayout.addView(toastView)

        val statusBarHeight = getStatusBarHeight(activity)
        toastView.translationY = -toastView.measuredHeight.toFloat() - statusBarHeight

        toastView.animate()
            .translationY(statusBarHeight.toFloat())
            .setDuration(200)
            .start()

        toastView.postDelayed({
            toastView.animate()
                .translationY(-toastView.height.toFloat() - statusBarHeight)
                .setDuration(300)
                .withEndAction {
                    parentLayout.removeView(toastView)

                    isShowing = false
                }
                .start()
        }, 2500)
    }

    @SuppressLint("InternalInsetResource")
    private fun getStatusBarHeight(activity: Activity): Int {
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}