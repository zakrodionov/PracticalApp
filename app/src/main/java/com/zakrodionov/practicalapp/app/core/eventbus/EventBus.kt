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

// EventBus для общих событий, если нужна какая-нибидь специализированная логика(например кеширование)
// создаем отдельный EventBus
open class EventBus : EventProvider, EventPublisher {

    private val events = MutableSharedFlow<Event>()

    override suspend fun provide(): SharedFlow<Event> = events

    override suspend fun publish(event: Event) {
        events.emit(event)
    }
}
