@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME
import java.time.temporal.WeekFields
import java.util.Locale

val weekFields get() = WeekFields.of(currentLocale)

/*
Внимание! Если в приложение реализуется смена языка, то DateTimeFormatter следует инициализировать после каждой
смены языка, например можно обернуть в object и изменять как var поля.
*/

/*
На уровне ViewModel, Interactor, Repository и т.д., лучше оперирывать OffsetDateTime или ZonedDateTime
и только на уровне вью мапить к LocalDateTime и отображать время
так при смене часового пояса на вью будет отображаться корректное время*/

/*
Для эффективного маппинга большого кол-ва дат, чтобы избежать лишние создание объектов, лучше выносить DateTimeFormatter
в отдельное поле, например:
fun showAllUserDates() {
    val formatter = dtfDate
    textView.text = userDates.map { it.format(formatter) }.joinToString()
}
*/

// Популярные форматы
val dtfDate get() = DateTimeFormatter.ofPattern("dd MM yyyy", currentLocale)
val dtfTime get() = DateTimeFormatter.ofPattern("HH:mm", currentLocale)
val dtfDateTime get() = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", currentLocale)

val dtfDateDots get() = DateTimeFormatter.ofPattern("dd.MM.yyyy", currentLocale)
val dtfDateDash get() = DateTimeFormatter.ofPattern("yyyy-MM-dd", currentLocale)

val dtfDateFullMonth get() = DateTimeFormatter.ofPattern("dd MMMM yyyy", currentLocale)
val dtfDateTimeFullMonth get() = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", currentLocale)
val dtfDateTimeFullMonthComma get() = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", currentLocale)
val dtfDateTimeFullMonthWithoutYear get() = DateTimeFormatter.ofPattern("dd MMMM HH:mm", currentLocale)

val dtfDateFirstYear get() = DateTimeFormatter.ofPattern("yyyy-MM-dd", currentLocale)
val dtfDateTimeWithOffset get() = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx", currentLocale)
val dtfPeriod get() = DateTimeFormatter.ofPattern("MM.yyyy", currentLocale)

val dtfDayFullMonth get() = DateTimeFormatter.ofPattern("dd MMMM", currentLocale)
val dtfDayShortMonth get() = DateTimeFormatter.ofPattern("dd MMM", currentLocale)

val dtfDayOfWeek get() = DateTimeFormatter.ofPattern("EEEE", currentLocale)
val dtfDayOfWeekShort get() = DateTimeFormatter.ofPattern("EEE", currentLocale)

fun String.parseOffsetDateTime(formatter: DateTimeFormatter = ISO_OFFSET_DATE_TIME): OffsetDateTime {
    val odt = OffsetDateTime.parse(this, formatter)
    return odt
}

fun String.parseZonedDateTime(formatter: DateTimeFormatter = ISO_ZONED_DATE_TIME): ZonedDateTime {
    val zdt = ZonedDateTime.parse(this, formatter)
    return zdt
}

fun Long.parseTimeStamp() =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(checkUnixTimeStamp()), currentZoneId)

@Suppress("MagicNumber")
fun Long.checkUnixTimeStamp() = if (this.toString().length <= 10) this * 1000L else this

// время с таймзоной в основном используется для отправки на сервер
fun nowZonedDateTime() = ZonedDateTime.ofInstant(Instant.now(), currentZoneId)

// время без таймзоны, используется для расчетов на устройстве
fun nowLocalDateTime() = LocalDateTime.now()

fun nowLocalTime() = LocalTime.now()

@Suppress("MagicNumber")
fun nowPlusDaysMillis(days: Long) = OffsetDateTime.now().plusDays(days).toEpochSecond() * 1000L

fun LocalDateTime?.format(pattern: String, locale: Locale = currentLocale) =
    this?.format(DateTimeFormatter.ofPattern(pattern, locale)) ?: ""

fun String?.changeDateFormat(
    sourceFormatter: DateTimeFormatter,
    targetFormatter: DateTimeFormatter
): String {
    this?.let { return LocalDate.parse(it, sourceFormatter).format(targetFormatter) } ?: return ""
}

fun String?.parseDateFirstYear() =
    if (isNullOrEmpty()) {
        LocalDate.now()
    } else {
        tryOrReturnDefault({ LocalDate.parse(this, dtfDateFirstYear) }, default = { LocalDate.now() })
    }

fun String?.parseDate() =
    if (isNullOrEmpty()) {
        LocalDate.now()
    } else {
        tryOrReturnDefault({ LocalDate.parse(this, dtfDate) }, default = { LocalDate.now() })
    }

fun String?.parseTime() =
    if (isNullOrEmpty()) {
        LocalTime.now()
    } else {
        tryOrReturnDefault({ LocalTime.parse(this, dtfTime) }, default = { LocalTime.now() })
    }

fun OffsetDateTime.toLocaleDateTimeApplyZone(zoneId: ZoneId = currentZoneId): LocalDateTime =
    LocalDateTime.ofInstant(toInstant(), zoneId)

fun ZonedDateTime.toLocaleDateTimeApplyZone(zoneId: ZoneId = currentZoneId): ZonedDateTime =
    ZonedDateTime.ofInstant(toInstant(), zoneId)

// Use for parse date like a '2020-12-28'
fun String.parseLocalDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)

// We bring the time from the device timezone to UTC
fun localDateAndTimeToOffsetDateTime(date: LocalDate, time: LocalTime, zoneId: ZoneId): OffsetDateTime =
    LocalDateTime.of(date, time)
        .atZone(zoneId)
        .withZoneSameInstant(ZoneOffset.UTC)
        .toOffsetDateTime()

fun LocalDateTime.getDayMonthFormatDate(): String = dtfDayFullMonth.format(this)

fun LocalDateTime.isSameDay(other: LocalDateTime): Boolean {
    val date: LocalDate = this.toLocalDate()
    val otherDate: LocalDate = other.toLocalDate()
    return date.isEqual(otherDate)
}

fun LocalDateTime.isYesterday(other: LocalDateTime): Boolean {
    val yesterdayLocalDate = other.toLocalDate().plusDays(-1)
    return toLocalDate().isEqual(yesterdayLocalDate)
}

fun LocalDateTime.isTomorrow(other: LocalDateTime): Boolean {
    val yesterdayLocalDate = other.toLocalDate().plusDays(+1)
    return toLocalDate().isEqual(yesterdayLocalDate)
}

fun LocalDateTime.numberOfDays(zoneId: ZoneId): Long {
    val nowDate = LocalDateTime.now().applyTimezone(zoneId)
    val dueDate = this.toLocalDate()
    return Duration.between(dueDate.atStartOfDay(), nowDate.atStartOfDay()).toDays()
}

fun LocalDateTime.applyTimezone(zoneId: ZoneId): LocalDate = LocalDateTime.of(toLocalDate(), toLocalTime())
    .atZone(ZoneOffset.UTC)
    .withZoneSameInstant(zoneId)
    .toLocalDate()
