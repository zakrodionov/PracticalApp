package com.zakrodionov.common.extensions

// e.g. 79991112223
const val LENGTH_PHONE_WITH_PREFIX = 11

fun String.isValidPhoneNumber(): Boolean = Regex("\\+79[0-9]{9}").matches(this)

fun String.isValidPhoneNumberLength(): Boolean = extractDigits().length == LENGTH_PHONE_WITH_PREFIX

fun String.isValidSmsCode(): Boolean = Regex("[0-9]{4}").matches(this)

fun String.isValidEmail(): Boolean =
    Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,6}").matches(this)

fun String.isValidDateDots(): Boolean = Regex("[0-9]{2}.[0-9]{2}.[0-9]{4}").matches(this)
