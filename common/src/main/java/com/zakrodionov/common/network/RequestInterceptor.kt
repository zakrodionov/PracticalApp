package com.zakrodionov.common.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .apply {
                interceptRequest(this)
            }
            .build()
        return chain.proceed(newRequest)
    }

    abstract fun interceptRequest(requestBuilder: Request.Builder)
}
