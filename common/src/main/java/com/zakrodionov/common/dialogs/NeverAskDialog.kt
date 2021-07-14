package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.actionApplicationSettings

class NeverAskDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)

        builder.setMessage(requireArguments().getInt(ARG_MESSAGE))
            .setPositiveButton(R.string.ok) { dialog, _ ->
                requireContext().actionApplicationSettings()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }

    companion object {
        const val TAG_NEVER_ASK_DIALOG = "TAG_NEVER_ASK_DIALOG"
        private const val ARG_MESSAGE = "ARG_MESSAGE"

        fun newInstance(@StringRes message: Int) = NeverAskDialog().apply {
            arguments = bundleOf(ARG_MESSAGE to message)
        }
    }
}
