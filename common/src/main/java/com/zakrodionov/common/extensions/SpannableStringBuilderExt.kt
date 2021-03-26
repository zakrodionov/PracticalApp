package com.zakrodionov.common.extensions

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.View
import androidx.annotation.StyleRes
import com.zakrodionov.common.custom.TouchableSpan

fun SpannableStringBuilder.spanBold(boldString: String): SpannableStringBuilder {
    if (isEmpty() || boldString.isEmpty()) {
        return this
    }
    val startIndex = indexOf(boldString)
    val lastIndex = startIndex + boldString.length
    if (startIndex != -1)
        setSpan(StyleSpan(Typeface.BOLD), startIndex, lastIndex, 0)
    return this
}

fun SpannableStringBuilder.spanTextAppearance(
    context: Context,
    string: String,
    @StyleRes style: Int
): SpannableStringBuilder {
    if (isEmpty() || string.isEmpty()) {
        return this
    }
    val startIndex = indexOf(string)
    val lastIndex = startIndex + string.length
    if (startIndex != -1)
        setSpan(TextAppearanceSpan(context, style), startIndex, lastIndex, 0)
    return this
}

inline fun SpannableStringBuilder.spanClickable(
    link: String,
    normalColor: Int,
    pressedColor: Int,
    withUnderLine: Boolean = true,
    crossinline func: (link: String) -> Unit
): SpannableStringBuilder {

    if (!contains(link) || isEmpty() || link.isEmpty()) {
        return this
    }

    val startIndex = indexOf(link)
    val lastIndex = startIndex + link.length

    setSpan(
        object : TouchableSpan(normalColor, pressedColor, withUnderLine) {
            override fun onClick(view: View) = func.invoke(link)
        },
        startIndex, lastIndex, 0
    )

    return this
}
