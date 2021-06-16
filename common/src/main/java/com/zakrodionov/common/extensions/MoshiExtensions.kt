package com.zakrodionov.common.extensions

import com.squareup.moshi.Moshi

// Убедитесь, что класс к которому будут применены функции расширения,
// помечен аннотацией @JsonClass(generateAdapter = true) !!!

inline fun <reified T : Any> T.toJson(moshi: Moshi): String = moshi.adapter(T::class.java).toJson(this)

inline fun <reified T : Any> String.fromJson(moshi: Moshi): T? = moshi.adapter(T::class.java).fromJson(this)
