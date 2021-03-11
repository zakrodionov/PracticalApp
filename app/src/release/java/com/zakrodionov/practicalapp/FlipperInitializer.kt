package com.zakrodionov.practicalapp

import android.content.Context
import com.facebook.flipper.core.FlipperClient

object FlipperInitializer {

    interface InitializationResult {
        // val okHttpClient: OkHttpClient?
    }

    fun initFlipperPlugins(context: Context, client: FlipperClient): InitializationResult {
        // Setup OkHttpClient
        return object : InitializationResult {}
    }
}
