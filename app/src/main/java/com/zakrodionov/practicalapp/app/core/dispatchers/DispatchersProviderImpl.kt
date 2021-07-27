package com.zakrodionov.practicalapp.app.core.dispatchers

import kotlinx.coroutines.Dispatchers

object DispatchersProviderImpl : DispatchersProvider {
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
    override val unconfined = Dispatchers.Unconfined
    override val io = Dispatchers.IO
}
