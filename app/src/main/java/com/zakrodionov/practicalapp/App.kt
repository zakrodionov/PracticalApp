package com.zakrodionov.practicalapp

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFlipper()
    }

    private fun setupFlipper() {
        FlipperInitializer.initFlipperPlugins(this)
    }
}
