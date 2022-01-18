package com.zakrodionov.common.network

import android.util.Base64


abstract class JwtDecoder {

    protected fun getHeader(jwt: String): String = splitJwt(jwt)[0].decodeBase64String()

    protected fun getPayload(jwt: String): String = splitJwt(jwt)[1].decodeBase64String()

    private fun splitJwt(jwt: String): List<String> = jwt.split("\\.".toRegex())

    private fun String.decodeBase64String(): String {
        val decodedBytes: ByteArray = Base64.decode(this, Base64.URL_SAFE)
        return String(decodedBytes, charset("UTF-8"))
    }

}





