@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat

fun TextView.setTextOrHide(value: String?) {
    isVisible = !value.isNullOrEmpty()
    if (!value.isNullOrEmpty()) {
        text = value
    }
}

fun TextView.setTextOrHide(predicate: () -> Boolean, value: () -> String?) {
    if (predicate.invoke()) {
        setTextOrHide(value.invoke())
    } else {
        gone()
    }
}

fun TextView.setTextOrHideParent(value: String?, parent: View?) {
    parent?.isVisible = !value.isNullOrEmpty()
    if (!value.isNullOrEmpty()) {
        text = value
    }
}

fun TextView.setTextCompatColor(@ColorRes color: Int) =
    setTextColor(ContextCompat.getColor(context, color))

fun TextView.setTextAppearanceCompat(textStyle: Int) =
    TextViewCompat.setTextAppearance(this, textStyle)

fun TextView.setBackgroundTintColor(@ColorInt color: Int?) {
    color?.let {
        DrawableCompat.setTint(DrawableCompat.wrap(background).mutate(), it)
    }
}

fun TextView.setBackgroundTintColorRes(@ColorRes color: Int) {
    DrawableCompat.setTint(
        DrawableCompat.wrap(background).mutate(),
        context.getCompatColor(color)
    )
}

fun TextView.setTextColorFromResources(context: Context, @ColorRes color: Int) {
    setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.startDrawable(@DrawableRes id: Int = 0) {
    setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

val TextView.textString get() = text.toString()

fun TextView.setStartImageSpan(string: String?, drawable: Drawable?, countSpace: Int = 3) =
    string?.let {
        val stringWithSpace = if (drawable != null) " ".repeat(countSpace).plus(it) else it

        val ssb = SpannableString(stringWithSpace)
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            ssb.setSpan(
                ImageSpan(it, ImageSpan.ALIGN_BASELINE),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        text = ssb
    }

fun TextView.setOnEnterClickListener(action: (TextView) -> Unit) {
    setOnEditorActionListener { textView, actionId, keyEvent ->
        if (keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
            action(textView)
        }
        false
    }
}
