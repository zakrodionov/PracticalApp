package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.zakrodionov.common.R
import com.zakrodionov.common.databinding.DialogCommonBinding
import com.zakrodionov.common.extensions.setTextAppearanceCompat
import com.zakrodionov.common.extensions.setTextOrHide
import kotlinx.parcelize.Parcelize

@Suppress("TooManyFunctions")
class CommonDialog : DialogFragment() {

    private var _binding: DialogCommonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCommonBinding.inflate(LayoutInflater.from(context))
        val theme = arguments?.getInt(THEME_KEY) ?: R.style.AlertDialog_Theme
        val builder = AlertDialog.Builder(requireContext(), theme)
        builder.setView(binding.root)

        setupTexts()
        setupButtons(builder)

        return builder.create()
    }

    private fun setupTexts() {
        with(binding) {
            val title = arguments?.getString(TITLE_KEY)
            val message = arguments?.getString(MESSAGE_KEY)
            val messageTextAppearance =
                arguments?.getInt(MESSAGE_APPEARANCE_KEY) ?: DEFAULT_VALUE_KEY

            tvTitle.setTextOrHide(title)
            tvMessage.setTextOrHide(message)

            if (messageTextAppearance != DEFAULT_VALUE_KEY) {
                tvMessage.setTextAppearanceCompat(messageTextAppearance)
            }
        }
    }

    private fun setupButtons(builder: AlertDialog.Builder) {
        val tag = arguments?.getString(TAG_KEY)
        val payload: Parcelable? = arguments?.getParcelable(PAYLOAD_KEY)

        var positiveButton = arguments?.getInt(BTN_POSITIVE_KEY)
        var negativeButton = arguments?.getInt(BTN_NEGATIVE_KEY)

        if (positiveButton == null || positiveButton == DEFAULT_VALUE_KEY) {
            positiveButton = R.string.ok
        }

        if (negativeButton == null || negativeButton == DEFAULT_VALUE_KEY) {
            negativeButton = R.string.cancel
        }

        val isNegativeVisible = arguments?.getBoolean(NEGATIVE_VISIBLE_KEY) ?: true

        builder.setPositiveButton(positiveButton) { _, _ ->
            cancelAndSendResult(CommonDialogEvent(DialogButton.OK, payload, tag))
        }

        if (isNegativeVisible) {
            builder.setNegativeButton(negativeButton) { _, _ ->
                cancelAndSendResult(CommonDialogEvent(DialogButton.CANCEL, payload, tag))
            }
        }
    }

    private fun cancelAndSendResult(commonDialogEvent: CommonDialogEvent) {
        setFragmentResult(RK_COMMON_DIALOG, bundleOf(ARG_COMMON_DIALOG_EVENT to commonDialogEvent))
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        isCancelable = arguments?.getBoolean(CANCELABLE_KEY) ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class Builder {

        private var title: String? = null
        private var message: String? = null
        private var messageTextAppearance: Int? = null
        private var tag: String? = null
        private var payload: Parcelable? = null
        private var btnPositive: Int? = null
        private var btnNegative: Int? = null
        private var reverse: Boolean = false
        private var negativeVisible: Boolean = false
        private var cancelable: Boolean = false
        private var withHtml: Boolean = false
        private var theme: Int = R.style.AlertDialog_Theme

        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun message(message: String?): Builder {
            this.message = message
            return this
        }

        fun messageTextAppearance(@StyleRes textAppearance: Int): Builder {
            this.messageTextAppearance = textAppearance
            return this
        }

        fun payload(payload: Parcelable): Builder {
            this.payload = payload
            return this
        }

        fun btnPositive(@StringRes btnPositive: Int): Builder {
            this.btnPositive = btnPositive
            return this
        }

        fun btnNegative(@StringRes btnNegative: Int): Builder {
            this.btnNegative = btnNegative
            return this
        }

        fun negativeVisible(negativeVisible: Boolean): Builder {
            this.negativeVisible = negativeVisible
            return this
        }

        fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun reverse(reverse: Boolean): Builder {
            this.reverse = reverse
            return this
        }

        fun cancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun withHtml(withHtml: Boolean): Builder {
            this.withHtml = withHtml
            return this
        }

        fun theme(theme: Int): Builder {
            this.theme = theme
            return this
        }

        fun build(): CommonDialog {
            val instance = CommonDialog()
            val args = Bundle()

            args.putString(TITLE_KEY, title)
            args.putString(MESSAGE_KEY, message)
            args.putInt(MESSAGE_APPEARANCE_KEY, messageTextAppearance ?: DEFAULT_VALUE_KEY)
            args.putParcelable(PAYLOAD_KEY, payload)
            args.putString(TAG_KEY, tag)
            args.putBoolean(REVERSE_KEY, reverse)
            args.putBoolean(CANCELABLE_KEY, cancelable)
            args.putBoolean(NEGATIVE_VISIBLE_KEY, negativeVisible)
            args.putInt(BTN_POSITIVE_KEY, btnPositive ?: DEFAULT_VALUE_KEY)
            args.putInt(BTN_NEGATIVE_KEY, btnNegative ?: DEFAULT_VALUE_KEY)
            args.putBoolean(WITH_HTML_KEY, withHtml)
            args.putInt(THEME_KEY, theme)

            instance.arguments = args
            return instance
        }
    }

    companion object {
        const val TAG_COMMON_DIALOG = "TAG_COMMON_DIALOG"
        const val RK_COMMON_DIALOG = "RK_COMMON_DIALOG"
        const val ARG_COMMON_DIALOG_EVENT = "ARG_COMMON_DIALOG_EVENT"

        const val TITLE_KEY = "TITLE_KEY"
        const val MESSAGE_KEY = "MESSAGE_KEY"
        const val MESSAGE_APPEARANCE_KEY = "MESSAGE_APPEARANCE_KEY"
        const val PAYLOAD_KEY = "PAYLOAD_KEY"
        const val TAG_KEY = "TAG_KEY"
        const val BTN_POSITIVE_KEY = "BTN_POSITIVE_KEY"
        const val BTN_NEGATIVE_KEY = "BTN_NEGATIVE_KEY"
        const val NEGATIVE_VISIBLE_KEY = "CANCEL_VISIBLE_KEY"
        const val REVERSE_KEY = "REVERSE_KEY"
        const val CANCELABLE_KEY = "CANCELABLE_KEY"
        const val WITH_HTML_KEY = "WITH_HTML_KEY"
        const val THEME_KEY = "THEME_KEY"

        const val DEFAULT_VALUE_KEY = 0
    }
}

@Parcelize
class CommonDialogEvent(
    val button: DialogButton,
    val payload: Parcelable?,
    val tag: String?
) : Parcelable

enum class DialogButton {
    OK,
    CANCEL
}
