package com.zakrodionov.practicalapp

import android.content.Context
import okhttp3.Interceptor

// No-op for release
object FlipperInitializer {

    val interceptor: Interceptor? = null

    fun initFlipperPlugins(@Suppress("UNUSED_PARAMETER") context: Context) = Unit
}
