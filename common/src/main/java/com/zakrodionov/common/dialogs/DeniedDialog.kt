package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.common.R

class DeniedDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)

        builder.setMessage(requireArguments().getInt(ARG_MESSAGE))
            .setPositiveButton(R.string.ok) { dialog, _ ->
                setFragmentResult(RK_DENIED_DIALOG, bundleOf())
                dialog.dismiss()
            }

        return builder.create()
    }

    companion object {
        const val TAG_DENIED_DIALOG = "TAG_DENIED_DIALOG"
        const val RK_DENIED_DIALOG = "RK_DENIED_DIALOG"
        private const val ARG_MESSAGE = "ARG_MESSAGE"

        fun newInstance(@StringRes message: Int) = DeniedDialog().apply {
            arguments = bundleOf(ARG_MESSAGE to message)
        }
    }
}
