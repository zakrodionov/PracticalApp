package com.zakrodionov.common.ui.models

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

sealed class ResourceString : Parcelable {

    companion object {
        val empty: ResourceString get() = Raw("")
    }

    @Parcelize
    data class Raw(val rawString: String) : ResourceString()

    @Parcelize
    data class Res(@StringRes val resId: Int) : ResourceString()

    fun getText(context: Context): String = when (this) {
        is Raw -> rawString
        is Res -> context.getString(resId)
    }

    fun getText(fragment: Fragment) = getText(context = fragment.requireContext())

    fun getText(activity: Activity) = getText(context = activity)

    fun applyTo(textView: TextView?) = when (this) {
        is Raw -> textView?.text = rawString
        is Res -> textView?.setText(resId)
    }
}
