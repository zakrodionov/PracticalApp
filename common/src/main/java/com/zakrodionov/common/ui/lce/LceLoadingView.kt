package com.zakrodionov.common.ui.lce

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zakrodionov.common.R
import com.zakrodionov.common.databinding.ViewLceLoadingBinding
import com.zakrodionov.common.extensions.getCompatColor
import com.zakrodionov.common.extensions.gone
import com.zakrodionov.common.extensions.show

class LceLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LceView {

    init {
        ViewLceLoadingBinding.inflate(LayoutInflater.from(context), this)
        isClickable = true
    }

    override fun renderState(state: LceState) {
        if (state is LoadingState) {
            show()
            val background =
                if (state.isTranslucent) Color.TRANSPARENT else context.getCompatColor(R.color.color_bg_layout)
            setBackgroundColor(background)
        } else {
            gone()
        }
    }
}
