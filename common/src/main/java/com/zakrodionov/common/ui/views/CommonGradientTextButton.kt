package com.zakrodionov.common.ui.views

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class CommonGradientTextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            setShaderOnText()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setShaderOnText()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        setShaderOnText()
    }

    private fun setShaderOnText() {
        if (width > 0) {
            // If text multiline then take the width of the button
            val textLength = minOf(paint.measureText(text.toString()), width.toFloat())
            val gradient = intArrayOf(
                // context.getCompatColor(R.color.color_start_gradient), //TODO
                // context.getCompatColor(R.color.color_end_gradient) //TODO
            )

            val shader: Shader = LinearGradient(
                width / 2.0f - textLength / 2.0f,
                0f,
                width / 2.0f + textLength / 2.0f,
                0f,
                gradient,
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = shader
        } else {
            paint.shader = null
        }
    }
}
