package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.zakrodionov.common.R

abstract class FixedBottomSheetDialogFragment(@LayoutRes open val layoutId: Int) :
    BottomSheetDialogFragment() {

    abstract val isFullScreen: Boolean

    /**
     *   По умолчанию, при [BottomSheetBehavior.STATE_EXPANDED] не применятеся [getTheme] shape, используем
     *   небольшой хак чтоб этого избежать если необходмио
     */
    open val applyCustomShapeInFullScreen: Boolean = true

    override fun getTheme(): Int = R.style.BottomSheetDialog_Rounded

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutId, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val parentLayout =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            if (isFullScreen && parentLayout != null) {

                if (applyCustomShapeInFullScreen) {
                    setBackground(parentLayout)
                }

                dialog.fixBottomSheetBehavior()
                setupFullScreen(parentLayout)
            }
        }

        return dialog
    }

    private fun BottomSheetDialog.fixBottomSheetBehavior() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) dismiss()
                if (newState == BottomSheetBehavior.STATE_EXPANDED && applyCustomShapeInFullScreen) {
                    setBackground(bottomSheet)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // if it needed change behavior of dialog with swipe
            }
        })
    }

    private fun setupFullScreen(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun setBackground(bottomSheet: View) {
        val newMaterialShapeDrawable = createMaterialShapeDrawable(bottomSheet)
        ViewCompat.setBackground(bottomSheet, newMaterialShapeDrawable)
    }

    open fun createMaterialShapeDrawable(bottomSheet: View): MaterialShapeDrawable? {
        val shapeAppearanceModel =
            // Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
            ShapeAppearanceModel.builder(context, 0, R.style.ShapeAppearance_BottomSheet_Rounded)
                .build()

        // Create a new MaterialShapeDrawable (you can't use the original MaterialShapeDrawable in the BottoSheet)
        val currentMaterialShapeDrawable = bottomSheet.background as MaterialShapeDrawable
        val newMaterialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        // Copy the attributes in the new MaterialShapeDrawable
        newMaterialShapeDrawable.initializeElevationOverlay(context)
        newMaterialShapeDrawable.fillColor = currentMaterialShapeDrawable.fillColor
        newMaterialShapeDrawable.tintList = currentMaterialShapeDrawable.tintList
        newMaterialShapeDrawable.elevation = currentMaterialShapeDrawable.elevation
        newMaterialShapeDrawable.strokeWidth = currentMaterialShapeDrawable.strokeWidth
        newMaterialShapeDrawable.strokeColor = currentMaterialShapeDrawable.strokeColor
        return newMaterialShapeDrawable
    }
}
