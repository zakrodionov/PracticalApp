package com.zakrodionov.practicalapp.app.features.bottom.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

interface Event

interface BottomTabsEventProvider {
    fun provide(): Flow<Event>

    // Зануляем событие при получении
    suspend fun handleEvent()
}

interface BottomTabsEventPublisher {
    suspend fun publish(event: Event)
}

open class BottomTabsEventBus : BottomTabsEventProvider, BottomTabsEventPublisher {

    private val events = MutableSharedFlow<Event?>(replay = 1, extraBufferCapacity = 1)

    override fun provide(): Flow<Event> = events.filterNotNull()

    override suspend fun publish(event: Event) {
        events.emit(event)
    }

    override suspend fun handleEvent() {
        events.emit(null)
    }
}

object SwitchTabToFavoriteEvent : Event
