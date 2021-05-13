package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.zakrodionov.common.R

class LceContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LceView {

    init {
        View.inflate(context, R.layout.view_lce_content, this)
    }

    override fun renderState(state: LceLayout.LceState) {
        val isContentState = state == LceLayout.LceState.ContentState
        val isTranslucent = state is LceLayout.LceState.LoadingState && state.isTranslucent
        visibility = if (isContentState || isTranslucent) View.VISIBLE else View.GONE
    }
}
