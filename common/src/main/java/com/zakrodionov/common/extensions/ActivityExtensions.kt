package com.zakrodionov.common.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Activity.rootView() = (findViewById<View>(android.R.id.content) as? ViewGroup)
    ?.getChildAt(0) as? ViewGroup

fun Activity.showToast(text: String?, length: Int = Toast.LENGTH_SHORT) {
    text?.let {
        toast(text, length)
    }
}

fun Activity.openApplicationSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
