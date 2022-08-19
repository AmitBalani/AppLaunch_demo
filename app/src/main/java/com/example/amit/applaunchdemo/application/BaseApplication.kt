package com.example.amit.applaunchdemo.application

import android.app.Application
import com.example.amit.applaunchdemo.storage.AppPref

class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPref.initialize(this)
    }

}
