package com.zakrodionov.practicalapp.app.data.network

import com.zakrodionov.common.extensions.headerBearer
import com.zakrodionov.common.network.RequestInterceptor
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import okhttp3.Request

class RequestHeaderInterceptor(
    private val appPreferences: AppPreferences,
) : RequestInterceptor() {

    override fun interceptRequest(requestBuilder: Request.Builder) {
        requestBuilder.header("app-id", "611a0d97fe9acec0ba2220cc") // Todo Test api

        if (appPreferences.accessToken.isNotBlank()) {
            requestBuilder.headerBearer(appPreferences.accessToken)
        }
    }
}
