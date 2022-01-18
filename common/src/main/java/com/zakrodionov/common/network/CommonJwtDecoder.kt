package com.zakrodionov.common.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

class CommonJwtDecoder(private val moshi: Moshi) : JwtDecoder() {

    fun getJwt(jwt: String): JwtPayload? =
        moshi.adapter(JwtPayload::class.java).fromJson(getPayload(jwt))
}

@JsonClass(generateAdapter = true)
data class JwtPayload(
    @Json(name = "exp")
    val exp: Long?,
    @Json(name = "iat")
    val iat: Long?,
    @Json(name = "jti")
    val jti: String?,
    @Json(name = "typ")
    val typ: Long?,
    @Json(name = "uid")
    val uid: Long?,
)
