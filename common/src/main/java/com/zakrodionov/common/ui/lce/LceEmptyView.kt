package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zakrodionov.common.R
import com.zakrodionov.common.databinding.ViewLceEmptyBinding
import com.zakrodionov.common.extensions.getCompatColor

class LceEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LceView {

    val binding = ViewLceEmptyBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isClickable = true
        setBackgroundColor(context.getCompatColor(R.color.color_bg_layout))
    }

    override fun renderState(state: LceLayout.LceState) {
        isVisible = state is LceLayout.LceState.EmptyState
    }

    fun customiseEmptyImage(@DrawableRes image: Int) {
        binding.ivIcon.setImageResource(image)
    }

    fun customiseEmptyMessage(@StringRes message: Int) {
        binding.tvMessage.setText(message)
    }

    fun customiseEmptyButtonVisibility(isButtonVisible: Boolean = false) {
        binding.btnEmpty.visibility = if (isButtonVisible) View.VISIBLE else View.GONE
    }

    fun customiseEmptyButtonText(@StringRes text: Int) {
        binding.btnEmpty.setText(text)
    }
}
