package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.addZeroPrefix
import com.zakrodionov.common.extensions.parseTime

class CustomTimePickerDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var payload: Parcelable? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        payload = arguments?.getParcelable(ARG_PAYLOAD)

        val stringTime = arguments?.getString(ARG_TIME)
        val time = stringTime.parseTime()

        return TimePickerDialog(activity, R.style.AlertDialog_Theme, this, time.hour, time.minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute_: Int) {
        val hour = hourOfDay.addZeroPrefix()
        val minute = minute_.addZeroPrefix()
        val time = "$hour:$minute"

        setFragmentResult(
            RK_CUSTOM_TIME_PICKER_DIALOG,
            bundleOf(ARG_PICKED_TIME to time, ARG_PAYLOAD to payload)
        )
    }

    companion object {
        const val RK_CUSTOM_TIME_PICKER_DIALOG = "RK_CUSTOM_TIME_PICKER_DIALOG"
        const val ARG_PICKED_TIME = "ARG_PICKED_TIME"

        private const val ARG_TIME = "ARG_TIME"
        private const val ARG_PAYLOAD = "ARG_PAYLOAD"

        fun newInstance(stringTime: String, payload: Parcelable? = null) =
            CustomTimePickerDialog().apply {
                arguments = bundleOf(
                    ARG_TIME to stringTime,
                    ARG_PAYLOAD to payload
                )
            }
    }
}
