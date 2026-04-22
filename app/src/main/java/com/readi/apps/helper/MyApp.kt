package com.readi.apps.helper

import android.app.Application
import com.readi.apps.network.RetrofitInstance

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitInstance.init(this)
    }
}