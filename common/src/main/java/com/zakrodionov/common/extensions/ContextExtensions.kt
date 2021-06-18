@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.isPortrait
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

val Context.isLandscape
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.permissionGranted(permissionName: String) =
    ActivityCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED

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

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
