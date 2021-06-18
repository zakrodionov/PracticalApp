package com.zakrodionov.common.ui.rv

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.common.extensions.dpToPx

class VerticalMarginItemDecoration(private val verticalMargin: Int = 20.dpToPx) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = verticalMargin
        outRect.bottom = verticalMargin
    }
}
