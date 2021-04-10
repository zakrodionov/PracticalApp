package com.zakrodionov.practicalapp.app.ui

import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : StubFragment(R.layout.fragment_favorite) {
    override val binding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override val viewModel: FavoriteViewModel by viewModel()
}

class FavoriteViewModel : StubViewModel() {
    override suspend fun onContentError(baseError: BaseError) = Unit
}
