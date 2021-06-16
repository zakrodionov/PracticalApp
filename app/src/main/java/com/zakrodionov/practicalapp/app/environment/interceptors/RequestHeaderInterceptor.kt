package com.zakrodionov.practicalapp.app.environment.interceptors

import com.zakrodionov.practicalapp.app.environment.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor(
    private val appPreferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header("app-id", "606b5b499129d5822c058cd0") // Todo Test api
            .apply {
                if (appPreferences.accessToken.isNotBlank()) {
                    headerBearer(appPreferences.accessToken)
                }
            }
            .build()
        return chain.proceed(newRequest)
    }
}
