package com.zakrodionov.common.ui.lce

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.gone
import com.zakrodionov.common.extensions.show

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
        val isLoadingState = state is LceLayout.LceState.LoadingState

        when {
            isContentState -> {
                show()
            }
            isLoadingState -> {
                return
            }
            else -> {
                gone()
            }
        }
    }
}
