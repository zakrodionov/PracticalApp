package com.zakrodionov.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.common.R
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.databinding.DialogCommonBinding
import com.zakrodionov.common.extensions.setTextAppearanceCompat
import com.zakrodionov.common.extensions.setTextOrHide
import kotlinx.parcelize.Parcelize

/**
 * How to handle result: define fragmentResultListener in activity/fragment
 * on childFragmentManager or supportFragmentManager(just type setFragmentResultListener())
 *
 * childFragmentManager.setFragmentResultListener(RK_COMMON_DIALOG, this@PostsFragment) { requestKey, bundle ->
 *     val result = bundle.getParcelable<CommonDialogEvent>(ARG_COMMON_DIALOG_EVENT)
 *     when {
 *         result?.tag == MY_TAG && result?.button == DialogButton.OK -> { /*do some action*/ }
 *     }
 * }
 * */
@Suppress("TooManyFunctions")
class CommonDialog : DialogFragment() {

    private var _binding: DialogCommonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCommonBinding.inflate(LayoutInflater.from(context))
        val theme = arguments?.getInt(THEME_KEY).takeIf { it != DEFAULT_VALUE_KEY } ?: R.style.AlertDialog
        val builder = MaterialAlertDialogBuilder(requireContext(), theme)
        builder.setView(binding.root)

        setupTexts()
        setupButtons(builder)

        return builder.create()
    }

    private fun setupTexts() {
        with(binding) {
            val title = arguments?.getParcelable<TextResource>(TITLE_KEY)
            val message = arguments?.getParcelable<TextResource>(MESSAGE_KEY)
            val messageTextAppearance = arguments?.getInt(MESSAGE_APPEARANCE_KEY).takeIf { it != DEFAULT_VALUE_KEY }

            tvTitle.setTextOrHide(title?.asString(resources))
            tvMessage.setTextOrHide(message?.asString(resources))

            if (messageTextAppearance != null) {
                tvMessage.setTextAppearanceCompat(messageTextAppearance)
            }
        }
    }

    private fun setupButtons(builder: AlertDialog.Builder) {
        val tag = arguments?.getString(TAG_KEY)
        val payload: Parcelable? = arguments?.getParcelable(PAYLOAD_KEY)

        var positiveButton = arguments?.getParcelable<TextResource>(BTN_POSITIVE_KEY)
        var negativeButton = arguments?.getParcelable<TextResource>(BTN_NEGATIVE_KEY)

        if (positiveButton == null) {
            positiveButton = TextResource.fromStringId(R.string.ok)
        }

        if (negativeButton == null) {
            negativeButton = TextResource.fromStringId(R.string.cancel)
        }

        builder.setPositiveButton(positiveButton.asString(resources)) { _, _ ->
            cancelAndSendResult(CommonDialogEvent(DialogButton.OK, payload, tag))
        }

        val isNegativeVisible = arguments?.getBoolean(NEGATIVE_VISIBLE_KEY) ?: true
        if (isNegativeVisible) {
            builder.setNegativeButton(negativeButton.asString(resources)) { _, _ ->
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

        private var title: TextResource? = null
        private var message: TextResource? = null
        private var messageTextAppearance: Int? = null
        private var tag: String? = null
        private var payload: Parcelable? = null
        private var btnPositive: TextResource? = null
        private var btnNegative: TextResource? = null
        private var negativeVisible: Boolean = false
        private var cancelable: Boolean = false
        private var theme: Int? = null

        fun title(title: TextResource?): Builder {
            this.title = title
            return this
        }

        fun message(message: TextResource?): Builder {
            this.message = message
            return this
        }

        fun messageTextAppearance(@StyleRes textAppearance: Int?): Builder {
            this.messageTextAppearance = textAppearance
            return this
        }

        fun payload(payload: Parcelable?): Builder {
            this.payload = payload
            return this
        }

        fun btnPositive(btnPositive: TextResource?): Builder {
            this.btnPositive = btnPositive
            return this
        }

        fun btnNegative(btnNegative: TextResource?): Builder {
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

        fun cancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun theme(theme: Int?): Builder {
            this.theme = theme
            return this
        }

        fun build(): CommonDialog {
            val instance = CommonDialog()
            val args = Bundle()

            args.putParcelable(TITLE_KEY, title)
            args.putParcelable(MESSAGE_KEY, message)
            args.putInt(MESSAGE_APPEARANCE_KEY, messageTextAppearance ?: DEFAULT_VALUE_KEY)
            args.putParcelable(PAYLOAD_KEY, payload)
            args.putString(TAG_KEY, tag)
            args.putBoolean(CANCELABLE_KEY, cancelable)
            args.putBoolean(NEGATIVE_VISIBLE_KEY, negativeVisible)
            args.putParcelable(BTN_POSITIVE_KEY, btnPositive)
            args.putParcelable(BTN_NEGATIVE_KEY, btnNegative)
            args.putInt(THEME_KEY, theme ?: DEFAULT_VALUE_KEY)

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
        const val CANCELABLE_KEY = "CANCELABLE_KEY"
        const val THEME_KEY = "THEME_KEY"

        // Because we can't put null int to bundle
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
