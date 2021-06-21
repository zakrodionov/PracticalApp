package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.zakrodionov.common.R
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.databinding.ViewLceErrorBinding
import com.zakrodionov.common.extensions.getCompatColor
import com.zakrodionov.common.extensions.gone
import com.zakrodionov.common.extensions.show

class LceErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LceView {

    val binding = ViewLceErrorBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        isClickable = true
        setBackgroundColor(context.getCompatColor(R.color.color_bg_layout))
    }

    override fun renderState(state: LceState) {
        if (state is ErrorState) {
            show()
            configureErrorView(state.error)
        } else {
            gone()
        }
    }

    private fun configureErrorView(error: UiError?) {
        with(binding) {
            if (error != null) {
                if (error.isNetworkError) {
                    ivIcon.setImageResource(R.drawable.ic_no_signal)
                } else {
                    ivIcon.setImageResource(R.drawable.ic_error)
                }

                tvTitle.text = error.title.asString(resources)
                tvMessage.text = error.message.asString(resources)

                btnTryAgain.show()
            }
        }
    }
}
