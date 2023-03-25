package com.zakrodionov.practicalapp.app.data.network

import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.tryOrNull
import com.zakrodionov.common.network.TokenAuthenticator
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.di.modules.basicOkHttpBuilder
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST

// Used to get 401 errors and updates the token
class RequestTokenAuthenticator(
    private val api: ApiAuthRefreshTokens,
    private val appPreferences: AppPreferences
) : TokenAuthenticator() {

    override fun getAccessToken(): String = appPreferences.accessToken

    override fun refreshTokens(): String? = runBlocking {
        val response = tryOrNull { api.refresh(appPreferences.refreshToken) }
        response?.let {
            appPreferences.accessToken = "" // TODO response.accessToken
            appPreferences.refreshToken = "" // TODO response.refreshToken

            "response.accessToken" // TODO return response.accessToken
        }
    }

    override fun logout() {
        super.logout()
        // TODO clear app data and send Logout event
        appPreferences.clearPreferences()
    }
}

// Special api for refresh tokens
interface ApiAuthRefreshTokens {
    @POST("/auth/refresh")
    suspend fun refresh(@Header("Authorization") refreshToken: String): Unit // TODO Return real body
}

// Provide in DI
fun buildAuthApi(moshi: Moshi): ApiAuthRefreshTokens {
    val okHttp = basicOkHttpBuilder().build()
    val authRetrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL) // TODO API_AUTH_URL
        .client(okHttp)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    return authRetrofit.create(ApiAuthRefreshTokens::class.java)
}
