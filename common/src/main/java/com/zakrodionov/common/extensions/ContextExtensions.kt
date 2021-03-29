@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat

val Context.networkInfo: NetworkInfo?
    get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.PORTRAIT_ORIENTATION
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

val Context.LANDSCAPE_ORIENTATION
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.permissionGranted(permissionName: String) =
    ActivityCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED

fun Context.toast(message: String?, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun Context.copyToClipboard(text: CharSequence, label: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}
