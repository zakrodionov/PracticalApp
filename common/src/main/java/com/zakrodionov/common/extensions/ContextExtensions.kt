@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.isPortrait
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

val Context.isLandscape
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    if (!message.isNullOrBlank()) {
        Toast.makeText(this, message, length).show()
    }
}

fun Context.copyToClipboard(text: CharSequence, label: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}
