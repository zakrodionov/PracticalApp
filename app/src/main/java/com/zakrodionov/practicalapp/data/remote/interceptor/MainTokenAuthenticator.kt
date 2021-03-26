package com.zakrodionov.practicalapp.data.remote.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// Used to get 401 errors and updates the token
class MainTokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val request = response.request
            .newBuilder()
            // .header("Authorization", "Bearer ${oauthManager.getSessionToken()}")
            .build()

        return request
    }
}
