package com.zakrodionov.practicalapp.app.environment.interceptors

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

// Used to get 401 errors and updates the token
class MainTokenAuthenticator @Inject constructor() : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val request = response.request
            .newBuilder()
            // .header("Authorization", "Bearer ${oauthManager.getSessionToken()}")
            .build()

        return request
    }
}
