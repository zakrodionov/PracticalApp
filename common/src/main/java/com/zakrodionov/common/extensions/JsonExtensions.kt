package com.zakrodionov.common.extensions

import org.json.JSONObject

fun JSONObject.getStringField(field: String): String {
    return if (has(field)) {
        getString(field) ?: ""
    } else {
        ""
    }
}

@Suppress("SwallowedException", "TooGenericExceptionCaught")
fun String?.safelyParseJson(): JSONObject =
    try {
        if (isNullOrBlank()) {
            JSONObject("{}")
        } else {
            JSONObject(this)
        }
    } catch (e: Exception) {
        JSONObject("{}")
    }
