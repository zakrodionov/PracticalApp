@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.graphics.Color
import android.telephony.PhoneNumberUtils
import android.util.Base64
import android.webkit.URLUtil
import java.util.Locale
import java.util.UUID

fun String.maxWord(max: Int, postfix: String = ""): String = split(" ").let { words ->
    if (words.size < max) return@let this

    words.take(max).joinToString(separator = " ", postfix = postfix).trim()
}

fun String.capitalizeFirstLetter(locale: Locale = Locale.ROOT) =
    lowercase(locale).replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }

@Suppress("SwallowedException", "TooGenericExceptionCaught")
fun String.lastSymbols(count: Int): String {
    if (count == 0) return ""
    return try {
        substring(length - count, length)
    } catch (e: Exception) {
        lastSymbols(count - 1)
    }
}

fun String.isHttpUrl(): Boolean {
    return (URLUtil.isHttpUrl(this) || URLUtil.isHttpsUrl(this))
}

fun randomUUID(): String = UUID.randomUUID().toString().replace("-", "")

fun String.encodeBase64ToString(): String =
    Base64.encodeToString(toByteArray(), Base64.NO_WRAP)

fun String?.removeDuplicateWhitespace() = this?.replace("\\s+".toRegex(), " ") ?: ""

fun String?.imageMimeType() =
    if (this?.endsWith(".png") == true) "image/png" else "image/jpeg"

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

@Suppress("MagicNumber")
fun String.hexToByte(): ByteArray? {
    if (!this.matches("[0-9a-fA-F]+".toRegex())) return null

    val len = this.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] =
            ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

@Suppress("SwallowedException", "TooGenericExceptionCaught")
fun String?.parseColor(): Int? {
    return try {
        Color.parseColor(this)
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun String.formatPhone(defaultCountryIso: String = currentLocale.country): String {
    var phone = this
    if (defaultCountryIso == "RU" && !phone.startsWith("8") && !phone.startsWith("+7"))
        phone = "+7$phone"

    phone = PhoneNumberUtils.formatNumber(phone, defaultCountryIso)

    return phone
}
