package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zakrodionov.common.databinding.LayoutLceBinding

class LceLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LceView {

    lateinit var binding: LayoutLceBinding
    lateinit var content: View

    var tryAgainButtonClickListener: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.errorView.binding.btnTryAgain.setOnClickListener(it) }
        }

    var emptyButtonClickListener: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.emptyView.binding.btnEmpty.setOnClickListener(it) }
        }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount > 1) throw IllegalStateException("Must have one child!")
        if (isInEditMode) return // Hide state views in designer

        content = getChildAt(0) // Assume first child as content
        binding = LayoutLceBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun renderState(state: LceState) {
        binding.loadingView.renderState(state)
        binding.emptyView.renderState(state)
        binding.errorView.renderState(state)
    }
}
