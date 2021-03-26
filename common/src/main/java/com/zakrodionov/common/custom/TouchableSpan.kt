package com.zakrodionov.common.custom

import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class TouchableSpan(
    private val normalTextColor: Int? = null,
    private val pressedTextColor: Int? = null,
    private val isUnderLine: Boolean = true
) : ClickableSpan() {

    private var isPressed: Boolean = false

    open fun setPressed(isSelected: Boolean) {
        isPressed = isSelected
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        if (normalTextColor != null && pressedTextColor != null) {
            ds.color = if (isPressed) pressedTextColor else normalTextColor
        }
        ds.isUnderlineText = isUnderLine
    }
}
