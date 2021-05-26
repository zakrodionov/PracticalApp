package com.zakrodionov.common.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.graphics.ColorUtils

/**
 * Получение цвета из аттрибута или [ColorRes]
 */
@ColorInt
fun Context.getColorFromAnyRes(someRes: Int): Int {
    return when (resources.getResourceTypeName(someRes)) {
        "attr" -> getAttrColor(someRes)
        else -> getCompatColor(someRes)
    }
}

/**
 * Получение drawable из аттрибута или [DrawableRes]
 */
fun Context.getDrawableFromAnyRes(someRes: Int): Drawable? {
    return when (resources.getResourceTypeName(someRes)) {
        "attr" -> getAttrDrawable(someRes)
        else -> getCompatDrawable(someRes)
    }
}

@ColorInt
fun Context.getAttrColor(@AttrRes attrColorId: Int): Int = obtainStyledAttributes(intArrayOf(attrColorId)).use {
    it.getColor(0, 0)
}

fun Context.getAttrDrawable(@AttrRes attrDrawableId: Int): Drawable? =
    obtainStyledAttributes(intArrayOf(attrDrawableId)).use {
        it.getDrawable(0)
    }

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getCompatDrawable(@DrawableRes drawable: Int) =
    AppCompatResources.getDrawable(this, drawable)

fun Context.getCompatColorStateList(@ColorRes color: Int): ColorStateList =
    AppCompatResources.getColorStateList(this, color)

@Suppress("MagicNumber")
val Int.isBrightColor: Boolean get() = ColorUtils.calculateLuminance(this) > 0.5
