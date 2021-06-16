package com.zakrodionov.common.network

import androidx.annotation.CallSuper
import com.zakrodionov.common.extensions.headerBearer
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// Used to catch a 401 error and then update the tokens
abstract class TokenAuthenticator : Authenticator {

    companion object {
        const val MAX_RETRY_COUNT = 3
    }

    abstract fun getAccessToken(): String

    @Volatile
    private var retryCount = 0

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = getAccessToken()

        if (accessToken.isEmpty()) {
            logout()
            return null
        }

        synchronized(this) {
            val newAccessToken = getAccessToken()

            return when {
                // Access token is refreshed in another thread.
                accessToken != newAccessToken -> {
                    retryCount = 0
                    response.buildRequest(newAccessToken)
                }
                retryCount < MAX_RETRY_COUNT -> {
                    retryCount++

                    // Need to refresh an access token
                    val token = refreshTokens()

                    if (token != null) {
                        retryCount = 0
                    }

                    response.buildRequest(token.orEmpty())
                }
                else -> {
                    logout()
                    null
                }
            }
        }
    }

    private fun Response.buildRequest(token: String) = request
        .newBuilder()
        .headerBearer(token)
        .build()

    @CallSuper
    open fun logout() {
        retryCount = 0
    }

    // Synchronous request(in coroutines runBlocking).
    // Refresh tokens, and then return access token or null
    abstract fun refreshTokens(): String?
}
