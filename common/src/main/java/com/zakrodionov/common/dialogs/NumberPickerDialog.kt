package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.zakrodionov.common.R
import com.zakrodionov.common.databinding.DialogNumberPickerBinding

class NumberPickerDialog : DialogFragment() {

    private var payload: Parcelable? = null

    private var _binding: DialogNumberPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogNumberPickerBinding.inflate(LayoutInflater.from(context))
        payload = arguments?.getParcelable(ARG_PAYLOAD)

        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialog_Theme)

        val minValue = arguments?.getInt(ARG_MIN_VALUE) ?: DEFAULT_MIN_VALUE
        val maxValue = arguments?.getInt(ARG_MAX_VALUE) ?: DEFAULT_MAX_VALUE
        val number = arguments?.getInt(ARG_NUMBER) ?: DEFAULT_MIN_VALUE

        with(binding) {
            numberPicker.minValue = minValue
            numberPicker.maxValue = maxValue
            numberPicker.value = number
        }

        builder.setView(binding.root)
        builder.setPositiveButton(R.string.ok) { _, _ ->
            binding.numberPicker.value.let {
                setFragmentResult(
                    RK_NUMBER_PICKER_DIALOG,
                    bundleOf(ARG_PICKED_NUMBER to it, ARG_PAYLOAD to payload)
                )
            }
        }
        return builder.create()
    }

    companion object {
        const val RK_NUMBER_PICKER_DIALOG = "RK_NUMBER_PICKER_DIALOG"
        const val ARG_PICKED_NUMBER = "ARG_PICKED_NUMBER"

        private const val ARG_NUMBER = "ARG_NUMBER"
        private const val ARG_MIN_VALUE = "ARG_MIN_VALUE"
        private const val ARG_MAX_VALUE = "ARG_MAX_VALUE"
        private const val ARG_PAYLOAD = "ARG_PAYLOAD"

        private const val DEFAULT_MIN_VALUE = 0
        private const val DEFAULT_MAX_VALUE = 100

        fun newInstance(
            number: Int?,
            minValue: Int? = DEFAULT_MIN_VALUE,
            maxValue: Int? = DEFAULT_MAX_VALUE,
            payload: Parcelable? = null
        ) =
            NumberPickerDialog().apply {
                arguments = bundleOf(
                    ARG_NUMBER to number,
                    ARG_MIN_VALUE to minValue,
                    ARG_MAX_VALUE to maxValue,
                    ARG_PAYLOAD to payload
                )
            }
    }
}
