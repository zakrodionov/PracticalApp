package com.zakrodionov.common.ui.rv

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.common.extensions.dpToPx

class HorizontalMarginItemDecoration(private val horizontalMargin: Int = 20.dpToPx) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizontalMargin
        outRect.right = horizontalMargin
    }
}
