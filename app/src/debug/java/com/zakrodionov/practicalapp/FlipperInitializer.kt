package com.zakrodionov.practicalapp

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.Interceptor

//  Usage interceptor
//  val client: OkHttpClient = OkHttpClient.Builder()
//        .apply {
//            FlipperInitializer.interceptor?.let { addInterceptor(it) }
//        }
//        .connectTimeout(60, TimeUnit.SECONDS)
//        .readTimeout(60, TimeUnit.SECONDS)
//        .writeTimeout(10, TimeUnit.MINUTES)
//        .build()

object FlipperInitializer {

    private val networkFlipperPlugin: NetworkFlipperPlugin by lazy {
        NetworkFlipperPlugin()
    }

    val interceptor: Interceptor? by lazy {
        FlipperOkhttpInterceptor(networkFlipperPlugin)
    }

    fun initFlipperPlugins(context: Context) {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(networkFlipperPlugin)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.start()
        }
    }
}
