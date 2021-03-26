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
import java.time.temporal.WeekFields
import java.util.Locale

val weekFields = WeekFields.of(currentLocale)

// Популярные форматы
// Внимание! Если в приложение реализуется смена языка, то DateTimeFormatter следует инициализировать после каждой
// смены языка, например можно обернуть в object и изменять как var поля.
val dtfDate by lazy { DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", currentLocale) }
val dtfDateFullMonth by lazy { DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", currentLocale) }
val dtfDateFullMonthComma by lazy { DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", currentLocale) }
val dtfDateFullMonthWithoutTime by lazy { DateTimeFormatter.ofPattern("dd MMMM yyyy", currentLocale) }
val dtfDateFullMonthWithoutYear by lazy { DateTimeFormatter.ofPattern("dd MMMM HH:mm", currentLocale) }
val dtfDayTimeFullMonth by lazy { DateTimeFormatter.ofPattern("dd MMMM HH:mm", currentLocale) }
val dtfOnlyTime by lazy { DateTimeFormatter.ofPattern("HH:mm", currentLocale) }
val dtfOnlyDate by lazy { DateTimeFormatter.ofPattern("dd MM yyyy", currentLocale) }
val dtfOnlyDateDots by lazy { DateTimeFormatter.ofPattern("dd.MM.yyyy", currentLocale) }
val dtfOnlyDateFirstYear by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd", currentLocale) }
val dtfWithOffset by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx", currentLocale) }
val dtfOnlyDateDash by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd", currentLocale) }
val dtfPeriod by lazy { DateTimeFormatter.ofPattern("MM.yyyy", currentLocale) }

val dtfDayFullMonth by lazy { DateTimeFormatter.ofPattern("dd MMMM", currentLocale) }
val dtfDayShortMonth by lazy { DateTimeFormatter.ofPattern("dd MMM", currentLocale) }

val dtfDayOfWeek by lazy { DateTimeFormatter.ofPattern("EEEE", currentLocale) }
val dtfDayOfWeekShort by lazy { DateTimeFormatter.ofPattern("EEE", currentLocale) }

fun String.parseDate(formatter: DateTimeFormatter = ISO_OFFSET_DATE_TIME): ZonedDateTime {
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
    targetFormatter: DateTimeFormatter = dtfOnlyDate
): String {
    this?.let {
        return LocalDate.parse(it, sourceFormatter).format(targetFormatter)
    } ?: return ""
}

fun String?.parseOnlyDateFirstYear() =
    if (this.isNullOrEmpty()) {
        LocalDate.now()
    } else {
        tryOrReturnDefault({ LocalDate.parse(this, dtfOnlyDateFirstYear) }, default = { LocalDate.now() })
    }

fun String?.parseOnlyDate() =
    if (isNullOrEmpty()) {
        LocalDate.now()
    } else {
        tryOrReturnDefault({ LocalDate.parse(this, dtfOnlyDate) }, default = { LocalDate.now() })
    }

fun String?.parseOnlyTime() =
    if (isNullOrEmpty()) {
        LocalTime.now()
    } else {
        tryOrReturnDefault({ LocalTime.parse(this, dtfOnlyTime) }, default = { LocalTime.now() })
    }

// Use for parse date like a '2020-12-11T17:30:00+00:00'
fun String.parseOffsetDateTime(): OffsetDateTime =
    OffsetDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

fun OffsetDateTime.toLocaleDateTimeApplyZone(zoneId: ZoneId = currentZoneId): LocalDateTime =
    LocalDateTime.ofInstant(toInstant(), zoneId)

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
