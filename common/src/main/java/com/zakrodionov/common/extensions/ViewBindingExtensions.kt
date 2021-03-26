package com.zakrodionov.common.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

val ViewBinding.context: Context get() = root.context

fun ViewBinding.getString(@StringRes resId: Int): String {
    return context.getString(resId)
}

fun ViewBinding.getString(@StringRes resId: Int, vararg formatArgs: Any): String {
    return context.getString(resId, *formatArgs)
}

@ColorInt
fun ViewBinding.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(context, id)
}

fun ViewBinding.getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(context, id)
}

fun ViewBinding.getColorStateList(@ColorRes id: Int): ColorStateList? {
    return ContextCompat.getColorStateList(context, id)
}
