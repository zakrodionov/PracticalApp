package com.zakrodionov.practicalapp.app.features.favorite.ui.favorite

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.dialogs.FixedBottomSheetDialogFragment
import com.zakrodionov.common.dialogs.NumberPickerDialog
import com.zakrodionov.common.dialogs.NumberPickerDialog.Companion.RK_NUMBER_PICKER_DIALOG
import com.zakrodionov.common.extensions.showDialog
import com.zakrodionov.common.extensions.showIfNotAlreadyShown
import com.zakrodionov.common.extensions.showToast
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.StubFragment
import com.zakrodionov.practicalapp.app.features.StubViewModel
import com.zakrodionov.practicalapp.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MagicNumber")
class FavoriteFragment : StubFragment(R.layout.fragment_favorite) {
    override val binding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override val viewModel: FavoriteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Test dialogs
        binding.tvShowCommonDialog.setOnClickListener {
            showDialog(
                childFragmentManager,
                "CommonDialog",
                TextResource.fromText("Hello"),
                TextResource.fromText("World!")
            )
        }
        binding.tvShowCustomCommonDialog.setOnClickListener {
            showDialog(
                childFragmentManager,
                "CommonCustomDialog",
                TextResource.fromText("Hello"),
                TextResource.fromText("Red World!"),
                messageTextAppearance = R.style.TextAppearance_MaterialComponents_Headline5,
                theme = R.style.AlertDialog_Theme_RedButtons
            )
        }
        binding.tvShowAnimatedDialog.setOnClickListener {
            AnimatedDialog().showIfNotAlreadyShown(childFragmentManager, "AnimatedDialog")
        }
        binding.tvShowBottomDialog.setOnClickListener {
            BottomDialog.newInstance(false).showIfNotAlreadyShown(childFragmentManager, "BottomDialog")
        }
        binding.tvShowBottomDialogFullscreen.setOnClickListener {
            BottomDialog.newInstance(true).showIfNotAlreadyShown(childFragmentManager, "BottomDialogFullscreen")
        }
        // Sample getting result
        childFragmentManager.setFragmentResultListener(RK_NUMBER_PICKER_DIALOG, this) { _, bundle ->
            val pickedNumber = bundle.getInt(NumberPickerDialog.ARG_PICKED_NUMBER)
            showToast(pickedNumber.toString())
        }
        binding.tvShowNumberDialog.setOnClickListener {
            NumberPickerDialog.newInstance(5, 0, 10)
                .showIfNotAlreadyShown(childFragmentManager, "NumberPickerDialog")
        }
    }
}

class FavoriteViewModel : StubViewModel()

class BottomDialog : FixedBottomSheetDialogFragment(R.layout.fragment_favorite) {
    override val isFullScreen: Boolean get() = requireArguments().getBoolean(ARG_IS_FULL_SCREEN)

    companion object {
        private const val ARG_IS_FULL_SCREEN = "ARG_IS_FULL_SCREEN"
        fun newInstance(isFullScreen: Boolean): BottomDialog = BottomDialog().apply {
            arguments = bundleOf(ARG_IS_FULL_SCREEN to isFullScreen)
        }
    }
}

class AnimatedDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog_Theme)
            .setView(R.layout.fragment_email)
            .create()
            .apply {
                window?.setWindowAnimations(R.style.DialogAnimation)
            }
    }
}
