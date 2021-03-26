@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getCompatDrawable(@DrawableRes drawable: Int) =
    AppCompatResources.getDrawable(this, drawable)

fun Context.getCompatColorStateList(@ColorRes color: Int): ColorStateList =
    AppCompatResources.getColorStateList(this, color)

@ColorInt
fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
    val textColor = typedArray.getColor(0, 0)
    typedArray.recycle()
    return textColor
}

fun Context.copyToClipboard(text: CharSequence, label: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}
