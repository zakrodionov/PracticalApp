package com.zakrodionov.practicalapp

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFlipper()
        setupTimber()
    }

    private fun setupFlipper() {
        FlipperInitializer.initFlipperPlugins(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
