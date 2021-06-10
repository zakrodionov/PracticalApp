package com.zakrodionov.practicalapp.app.di.module

import com.zakrodionov.practicalapp.app.core.ErrorHandler
import com.zakrodionov.practicalapp.app.core.ErrorHandlerImpl
import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.ExecutorImpl
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProviderImpl
import com.zakrodionov.practicalapp.app.core.eventbus.EventBus
import com.zakrodionov.practicalapp.app.core.eventbus.EventProvider
import com.zakrodionov.practicalapp.app.core.eventbus.EventPublisher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun provideDispatchersProvider(dispatchersProviderImpl: DispatchersProviderImpl): DispatchersProvider

    @Binds
    abstract fun provideErrorHandler(errorHandler: ErrorHandlerImpl): ErrorHandler

    @Binds
    abstract fun provideExecutor(executorImpl: ExecutorImpl): Executor

    @Binds
    abstract fun provideEventProvider(eventBus: EventBus): EventProvider

    @Binds
    abstract fun provideEventPublisher(eventBus: EventBus): EventPublisher

}
