package com.zakrodionov.common.ui.lce

sealed class LceState

class LoadingState(val isTranslucent: Boolean = false) : LceState()
object ContentState : LceState()
object EmptyState : LceState()
class ErrorState(val error: UiError) : LceState()
