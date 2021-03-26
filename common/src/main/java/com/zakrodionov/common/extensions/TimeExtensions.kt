package com.zakrodionov.common.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val dtfHoursMinutes by lazy { DateTimeFormatter.ofPattern("HH:mm", currentLocale) }

// Use for parse date like a '19:07'
fun String.parseLocalTime(): LocalTime = LocalTime.parse(this, dtfHoursMinutes)

// When the time comes from the server without a time zone (in UTC), but it needs to be applied,
// we substitute the zone ourselves
fun LocalTime.applyZone(zoneId: ZoneId) = LocalDateTime.of(LocalDate.now(), this)
    .atZone(ZoneOffset.UTC)
    .withZoneSameInstant(zoneId)
    .toLocalTime()

fun LocalTime.getStringTime(): String = dtfHoursMinutes.format(this)

/**
 * @return time in format 'HH:mm'(19:07)
 */
fun LocalDateTime.getStringTime(): String = dtfHoursMinutes.format(this)

// Use for parse date like a '2020-12-11T17:30:00'
fun String.parseNoTimezoneDateTime(): LocalDateTime = LocalDateTime.parse(
    this,
    DateTimeFormatter.ISO_LOCAL_DATE_TIME
)

fun String.parseTimeWithTimezone(zoneId: ZoneId): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .atZone(ZoneOffset.UTC)
        .withZoneSameInstant(zoneId)
        .toLocalDateTime()

fun LocalDateTime.applyTimezone(zoneId: ZoneId): LocalDate = LocalDateTime.of(toLocalDate(), toLocalTime())
    .atZone(ZoneOffset.UTC)
    .withZoneSameInstant(zoneId)
    .toLocalDate()

fun LocalDate.parseToLocalDateTime(): LocalDateTime = atTime(0, 0)
