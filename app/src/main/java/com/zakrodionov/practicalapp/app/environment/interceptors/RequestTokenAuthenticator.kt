package com.zakrodionov.practicalapp.app.environment.interceptors

import com.squareup.moshi.Moshi
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.app.di.initializer.NetInitializer.basicOkHttpBuilder
import com.zakrodionov.practicalapp.app.environment.preferences.ApplicationSettings
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST

// Used to get 401 errors and updates the token
class RequestTokenAuthenticator(
    private val api: ApiAuthRefreshTokens,
    private val applicationSettings: ApplicationSettings,
) : TokenAuthenticator() {

    override fun getAccessToken(): String = applicationSettings.accessToken

    override fun refreshTokens(): String? = runBlocking {
        val response = api.refresh(applicationSettings.refreshToken)
        applicationSettings.accessToken = "" // TODO response.accessToken
        applicationSettings.refreshToken = "" // TODO response.refreshToken
        response
        // TODO return response.accessToken
        null
    }

    override fun logout() {
        super.logout()
        // TODO clear app data and send Logout event
        applicationSettings.clearSettings()
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
