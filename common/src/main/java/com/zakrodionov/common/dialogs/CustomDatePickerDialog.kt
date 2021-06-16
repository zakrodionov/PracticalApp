package com.zakrodionov.common.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.addZeroPrefix
import com.zakrodionov.common.extensions.parseDate
import com.zakrodionov.common.extensions.parseDateFirstYear

class CustomDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var payload: Parcelable? = null
    private var reverseFormat: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        payload = arguments?.getParcelable(ARG_PAYLOAD)
        reverseFormat = arguments?.getBoolean(ARG_REVERSE_FORMAT) ?: false

        val stringDate = arguments?.getString(ARG_DATE)
        val minDate = arguments?.getLong(ARG_MIN_DATE)

        val date = if (reverseFormat) stringDate.parseDateFirstYear() else stringDate.parseDate()

        val dialog = DatePickerDialog(
            requireContext(),
            R.style.AlertDialog_Theme,
            this,
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        )

        minDate?.let {
            dialog.datePicker.minDate = minDate
        }

        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val realMonth = month + 1
        val monthTwoDigit = realMonth.addZeroPrefix()
        val dayTwoDigit = day.addZeroPrefix()

        val stringDate = if (reverseFormat)
            "$year.$monthTwoDigit.$dayTwoDigit"
        else
            "$dayTwoDigit.$monthTwoDigit.$year"

        setFragmentResult(
            RK_CUSTOM_DATE_PICKER_DIALOG,
            bundleOf(ARG_PICKED_DATE to stringDate, ARG_PAYLOAD to payload)
        )
    }

    companion object {
        const val RK_CUSTOM_DATE_PICKER_DIALOG = "RK_CUSTOM_DATE_PICKER_DIALOG"
        const val ARG_PICKED_DATE = "ARG_PICKED_DATE"

        private const val ARG_DATE = "ARG_DATE"
        private const val ARG_MIN_DATE = "ARG_MIN_DATE"
        private const val ARG_REVERSE_FORMAT = "ARG_REVERSE_FORMAT"

        fun newInstance(
            stringDate: String,
            minDate: Long? = null,
            payload: Parcelable? = null,
            reverseFormat: Boolean = false
        ) =
            CustomDatePickerDialog().apply {
                arguments = bundleOf(
                    ARG_DATE to stringDate,
                    ARG_MIN_DATE to minDate,
                    ARG_PAYLOAD to payload,
                    ARG_REVERSE_FORMAT to reverseFormat
                )
            }
    }
}
