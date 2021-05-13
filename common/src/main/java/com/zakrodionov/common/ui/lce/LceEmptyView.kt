package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.zakrodionov.common.databinding.ViewLceEmptyBinding
import com.zakrodionov.common.extensions.gone
import com.zakrodionov.common.extensions.show

class LceEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LceView {

    val binding = ViewLceEmptyBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun renderState(state: LceLayout.LceState) {
        visibility = if (state == LceLayout.LceState.EmptyState) View.VISIBLE else View.GONE
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
