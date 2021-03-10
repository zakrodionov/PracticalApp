package com.zakrodionov.practicalapp

import android.app.Application
//import com.facebook.flipper.android.AndroidFlipperClient
//import com.facebook.flipper.android.utils.FlipperUtils
//import com.facebook.flipper.core.FlipperClient
//import com.facebook.flipper.plugins.inspector.DescriptorMapping
//import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
//import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
//import com.facebook.soloader.SoLoader
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFlipper()
        setupTimber()
    }

    private fun setupFlipper() {
//        SoLoader.init(this, false)
//
//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
//            val client: FlipperClient = AndroidFlipperClient.getInstance(this)
//            client.addPlugin(NetworkFlipperPlugin()) // Todo add to okhttp
//            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
//            client.start()
//        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
