package com.zakrodionov.practicalapp.data.remote.interceptor

import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import okhttp3.Interceptor
import okhttp3.Response

class MainHeaderInterceptor(
    private val applicationSettings: ApplicationSettings
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header("app-id", "6059b71861d6972ad9b55718") // Todo Test api
            .apply {
                if (applicationSettings.token.isNotBlank()) {
                    addHeader("Authorization", "Bearer ${applicationSettings.token}")
                }
            }
            .build()
        return chain.proceed(newRequest)
    }
}
