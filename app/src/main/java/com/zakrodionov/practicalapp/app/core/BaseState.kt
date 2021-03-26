package com.zakrodionov.practicalapp.app.core

interface BaseState {
    val error: BaseError?
    val isLoading: Boolean
}
