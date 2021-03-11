package com.zakrodionov.practicalapp

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

object FlipperInitializer {

    interface InitializationResult {
        // val okHttpClient: OkHttpClient?
    }

    fun initFlipperPlugins(context: Context): InitializationResult {
        SoLoader.init(context, false)

        // Setup OkHttpClient

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(NetworkFlipperPlugin()) // Todo add to okhttp
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.start()
        }

        return object : InitializationResult {}
    }
}
