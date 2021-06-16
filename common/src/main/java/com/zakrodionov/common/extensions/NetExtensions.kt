package com.zakrodionov.common.extensions

import okhttp3.Request

fun Request.Builder.headerBearer(accessToken: String) =
    header("Authorization", "Bearer $accessToken")
