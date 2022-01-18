package com.zakrodionov.practicalapp.app.core.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface Event

interface EventSubscriber {
    fun subscribe(): SharedFlow<Event>
}

interface EventSender {
    suspend fun send(event: Event)
}

// EventBus для общих событий, если нужна какая-нибидь специализированная логика(например кеширование)
// создаем отдельный EventBus
open class EventBus : EventSubscriber, EventSender {

    private val events = MutableSharedFlow<Event>()

    override fun subscribe(): SharedFlow<Event> = events

    override suspend fun send(event: Event) {
        events.emit(event)
    }
}
