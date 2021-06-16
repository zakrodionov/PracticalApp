package com.zakrodionov.common.net

import android.util.Base64
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.UnsupportedEncodingException

class JwtDecoder(private val moshi: Moshi) {

    fun getUserId(jwt: String): String = getJwtBody(jwt)?.uid?.toString() ?: ""

    private fun getJwtBody(jwt: String): JwtBody? =
        moshi.adapter(JwtBody::class.java).fromJson(getJsonBody(jwt))

    private fun getJsonBody(jwt: String) = getJson(decoded(jwt)[1])

    @Throws(Exception::class)
    private fun decoded(jwt: String): Array<String> {
        try {
            val split = jwt.split("\\.".toRegex()).toTypedArray()
            return split
        } catch (e: UnsupportedEncodingException) {
            Timber.e(e)
            throw e
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, charset("UTF-8"))
    }

    @JsonClass(generateAdapter = true)
    data class JwtBody(
        @Json(name = "exp")
        val exp: Long?,
        @Json(name = "iat")
        val iat: Long?,
        @Json(name = "jti")
        val jti: String?,
        @Json(name = "new")
        val new: Boolean?,
        @Json(name = "priv")
        val priv: List<String?>?,
        @Json(name = "typ")
        val typ: Long?,
        @Json(name = "uid")
        val uid: Long?
    )
}
