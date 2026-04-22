package com.readi.apps.helper

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.NestedScrollingChild
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.RecyclerView
import android.provider.Settings
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LiveData
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.messaging.FirebaseMessaging
import com.readi.apps.R
import com.readi.apps.main.splashandonboardingscreens.SplashActivity
import java.util.TimeZone
import kotlin.math.abs

open class BaseActivity : AppCompatActivity() {
    private var startX = 0f
    private var startY = 0f
    private val SCROLL_THRESHOLD = 10

    open fun handleBackPress() {
        finish()
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })

        handleStatusBars()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun handleStatusBars() {
        window.decorView.post {
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.WHITE
            window.isNavigationBarContrastEnforced = false
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }
    }
    fun applyBottomInset(view: View) {
        val originalBottomPadding = view.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                originalBottomPadding + systemBars.bottom
            )
            insets
        }
    }
    /** Hide keyboard on tap outside EditText but allow scrolling */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.rawX
                startY = ev.rawY
            }
            MotionEvent.ACTION_UP -> {
                val endX = ev.rawX
                val endY = ev.rawY
                val dx = abs(endX - startX)
                val dy = abs(endY - startY)

                if (dx < SCROLL_THRESHOLD && dy < SCROLL_THRESHOLD) {
                    currentFocus?.let { view ->
                        if (view is EditText) {
                            val outRect = android.graphics.Rect()
                            view.getGlobalVisibleRect(outRect)
                            if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                                val touchedView = findViewAt(window.decorView.rootView, ev.rawX.toInt(), ev.rawY.toInt())

                                val shouldKeepKeyboard = touchedView?.tag == "keyboard_persist"
                                        || touchedView is ScrollView
                                        || touchedView is RecyclerView
                                        || touchedView is NestedScrollingChild

                                if (!shouldKeepKeyboard) {
                                    hideKeyboard()
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    /** Recursively find the view at the given screen coordinates */
    private fun findViewAt(rootView: View, x: Int, y: Int): View? {
        if (rootView !is ViewGroup) {
            return if (rootView.isShown) rootView else null
        }

        for (i in rootView.childCount - 1 downTo 0) {
            val child = rootView.getChildAt(i)

            if (!child.isShown) continue

            val location = IntArray(2)
            child.getLocationOnScreen(location)
            val rect = android.graphics.Rect(
                location[0],
                location[1],
                location[0] + child.width,
                location[1] + child.height
            )
            if (rect.contains(x, y)) {
                val found = findViewAt(child, x, y)
                if (found != null) return found
            }
        }
        return null
    }
    /** Hide keyboard and clear focus */
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }
    @SuppressLint("HardwareIds")
    fun getAndroidDeviceId(): String {
        return Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: ""
    }
    fun getTimezone(): String {
        return TimeZone.getDefault().id ?: "Asia/Karachi"
    }
    fun getFCMToken(onToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM_TOKEN", "Token: ${task.result}")

                onToken(task.result)
            } else {
                Log.e("FCM_TOKEN", "Failed: ${task.exception}")
            }
        }
    }
    protected fun observeLoading(loading: LiveData<Boolean>) {
        loading.observe(this) { isLoading ->
            val overlay = findViewById<FrameLayout>(R.id.loadingOverlay)
            val lottie = findViewById<LottieAnimationView>(R.id.loadingAnimationView)

            if (isLoading) {
                overlay.visibility = View.VISIBLE
                lottie.visibility = View.VISIBLE
                lottie.playAnimation()
            } else {
                overlay.visibility = View.GONE
                lottie.cancelAnimation()
                lottie.visibility = View.GONE
            }
        }
    }

    /** next transition */
    @Suppress("DEPRECATION")
    fun applyNextTransition() {
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right)
    }

    /** back transition */
    @Suppress("DEPRECATION")
    fun applyBackTransition(){
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
    }

    override fun onPause() {
        super.onPause()

        if (this !is SplashActivity) {
            SessionManager(this).setLastActivity(this::class.java.simpleName)
        }
    }
}