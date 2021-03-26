package com.zakrodionov.common.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import com.zakrodionov.common.utils.isGreaterThanL
import com.zakrodionov.common.utils.isGreaterThanM

fun Activity.rootView() = (findViewById<View>(android.R.id.content) as? ViewGroup)
    ?.getChildAt(0) as? ViewGroup

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    if (isGreaterThanL()) {
        window.statusBarColor = color
    }
}

fun Activity.setStatusBarLightMode(
    isLightMode: Boolean
) {
    if (isGreaterThanM()) {
        val decorView = this.window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (isLightMode) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }
}

fun Activity.showToast(text: String?, length: Int = Toast.LENGTH_SHORT) {
    text?.let {
        toast(text, length)
    }
}

fun Activity.setKeyboardModPan() {
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

fun Activity.setKeyboardModResize() {
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun Activity.setKeyboardModResizeAndHidden() {
    window?.setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
    )
}

fun Activity.openApplicationSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
