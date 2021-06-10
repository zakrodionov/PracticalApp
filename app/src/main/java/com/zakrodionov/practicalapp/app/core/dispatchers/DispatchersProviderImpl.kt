package com.zakrodionov.practicalapp.app.core.dispatchers

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main.immediate
    override val unconfined = Dispatchers.Unconfined
    override val io = Dispatchers.IO
}
