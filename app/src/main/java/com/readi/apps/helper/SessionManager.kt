package com.readi.apps.helper

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_session", Context.MODE_PRIVATE)

    fun setLastActivity(activityName: String) {
        prefs.edit { putString("last_activity", activityName) }
    }

    fun getLastActivity(): String? {
        return prefs.getString("last_activity", null)
    }

    fun clearSession() {
        prefs.edit { clear() }
    }
}