package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.core.ErrorHandler
import com.zakrodionov.practicalapp.app.core.ErrorHandlerImpl
import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.ExecutorImpl
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProviderImpl
import com.zakrodionov.practicalapp.app.core.eventbus.EventBus
import com.zakrodionov.practicalapp.app.core.eventbus.EventProvider
import com.zakrodionov.practicalapp.app.core.eventbus.EventPublisher
import org.koin.core.module.Module

object CoreInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single<DispatchersProvider> { DispatchersProviderImpl }
            single<ErrorHandler> { ErrorHandlerImpl(get()) }
            single<Executor> { ExecutorImpl(get(), get()) }

            val eventBus = EventBus()
            single<EventProvider> { eventBus }
            single<EventPublisher> { eventBus }
        }
    }
}
