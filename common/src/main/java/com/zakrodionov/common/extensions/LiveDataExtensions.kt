package com.zakrodionov.common.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.common.custom.Event

fun <T : Any?> MutableLiveData<T>.refresh() = apply { setValue(value) }

fun <T : Any?> MutableLiveData<Event<T>>.repeat() = apply {
    value?.let {
        value = Event(it.peekContent())
    }
}

// Достаем из лайвдаты(со списком) данные по индексу хранящимся в другой лайвдате
fun <T, L : List<T>> LiveData<L>.getOrNull(liveDataInt: LiveData<Int>) = value?.getOrNull(liveDataInt.value ?: -1)
