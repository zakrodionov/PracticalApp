package com.zakrodionov.practicalapp.app.data.network

import com.zakrodionov.common.extensions.headerBearer
import com.zakrodionov.common.network.RequestInterceptor
import com.zakrodionov.practicalapp.app.data.persistence.AppPreferences
import okhttp3.Request

class RequestHeaderInterceptor(
    private val appPreferences: AppPreferences,
) : RequestInterceptor() {

    override fun interceptRequest(requestBuilder: Request.Builder) {
        requestBuilder.header("app-id", "606b5b499129d5822c058cd0") // Todo Test api

        if (appPreferences.accessToken.isNotBlank()) {
            requestBuilder.headerBearer(appPreferences.accessToken)
        }
    }
}
