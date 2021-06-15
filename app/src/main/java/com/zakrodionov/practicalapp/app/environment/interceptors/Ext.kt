package com.zakrodionov.practicalapp.app.environment.interceptors

import okhttp3.Request

fun Request.Builder.headerBearer(accessToken: String) =
    header("Authorization", "Bearer $accessToken")
