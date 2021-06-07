package com.zakrodionov.practicalapp.app.features.favorite.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.dialogs.FixedBottomSheetDialogFragment
import com.zakrodionov.common.dialogs.NumberPickerDialog
import com.zakrodionov.common.extensions.showDialog
import com.zakrodionov.common.extensions.showIfNotAlreadyShown
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.StubFragment
import com.zakrodionov.practicalapp.app.features.StubViewModel
import com.zakrodionov.practicalapp.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        binding.tvShowBottomDialog.setOnClickListener {
            BottomDialog.newInstance(false).showIfNotAlreadyShown(childFragmentManager, "BottomDialog")
        }
        binding.tvShowBottomDialogFullscreen.setOnClickListener {
            BottomDialog.newInstance(true).showIfNotAlreadyShown(childFragmentManager, "BottomDialogFullscreen")
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
