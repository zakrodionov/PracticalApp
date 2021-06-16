package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.dpToPx
import com.zakrodionov.common.extensions.getCompatDrawable

class ItemPickerDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val payload = arguments?.getParcelable<Parcelable>(ARG_PAYLOAD)
        val items = arguments?.getStringArrayList(ARG_ITEMS)?.toTypedArray()

        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog_Theme)
            .apply {
                setItems(items) { _, position ->
                    setFragmentResult(
                        RK_ITEM_PICKER_DIALOG,
                        bundleOf(ARG_PICKED_ITEM_POSITION to position, ARG_PAYLOAD to payload)
                    )
                    dismiss()
                }
                setPositiveButton(R.string.close, null)
            }
            .create()
            .apply {
                listView?.divider = context.getCompatDrawable(R.drawable.list_divider)
                listView?.dividerHeight = 1.dpToPx
            }

        return dialog
    }

    companion object {
        const val RK_ITEM_PICKER_DIALOG = "RK_ITEM_PICKER_DIALOG"
        const val ARG_PICKED_ITEM_POSITION = "ARG_PICKED_ITEM_POSITION"

        private const val ARG_ITEMS = "ARG_ITEMS"
        private const val ARG_PAYLOAD = "ARG_PAYLOAD"

        fun newInstance(items: ArrayList<String>, payload: Parcelable? = null) =
            ItemPickerDialog().apply {
                arguments = bundleOf(
                    ARG_ITEMS to items,
                    ARG_PAYLOAD to payload
                )
            }
    }
}
