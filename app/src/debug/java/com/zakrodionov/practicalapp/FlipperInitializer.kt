package com.zakrodionov.practicalapp

import android.content.Context
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

object FlipperInitializer {

    interface InitializationResult {
        // val okHttpClient: OkHttpClient?
    }

    fun initFlipperPlugins(context: Context, client: FlipperClient): InitializationResult {
        SoLoader.init(context, false)

        // Setup OkHttpClient

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            client.addPlugin(NetworkFlipperPlugin()) // Todo add to okhttp
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.start()
        }

        return object : InitializationResult {}
    }
}
