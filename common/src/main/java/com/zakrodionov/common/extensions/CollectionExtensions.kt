package com.zakrodionov.common.extensions

fun <T> MutableList<T>.replaceOn(newItems: List<T>) = apply {
    clear()
    addAll(newItems)
}

fun <K, V> MutableMap<K, V>.putIfEmpty(key: K, value: V) = apply {
    if (!contains(key)) put(key, value)
}

fun <T> MutableList<T>.replace(oldItem: T, newItem: T) {
    val index = indexOf(oldItem)
    removeAt(index)
    add(index, newItem)
}

fun <T> List<T>.multiple(x: Int): MutableList<T> {
    val list = mutableListOf<T>()
    (0..x).forEach { _ ->
        list.addAll(this)
    }
    return list
}
