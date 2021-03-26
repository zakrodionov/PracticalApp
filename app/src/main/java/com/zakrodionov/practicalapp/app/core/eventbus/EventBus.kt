package com.zakrodionov.practicalapp.app.core.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface Event

interface EventProvider {
    suspend fun provide(): SharedFlow<Event>
}

interface EventPublisher {
    suspend fun publish(event: Event)
}

open class EventBus : EventProvider, EventPublisher {

    private val events = MutableSharedFlow<Event>()

    override suspend fun provide(): SharedFlow<Event> = events

    override suspend fun publish(event: Event) {
        events.emit(event)
    }
}
