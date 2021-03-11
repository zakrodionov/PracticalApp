package com.zakrodionov.practicalapp

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFlipper()
    }

    private fun setupFlipper() {
        FlipperInitializer.initFlipperPlugins(this, AndroidFlipperClient.getInstance(this))
    }
}
