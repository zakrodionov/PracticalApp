package com.zakrodionov.common.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

fun Double.round(
    scale: Int = 2,
    roundingMode: RoundingMode = RoundingMode.HALF_EVEN
): String = BigDecimal(this).round(scale, roundingMode).toString()

fun BigDecimal.round(
    scale: Int = 2,
    roundingMode: RoundingMode = RoundingMode.HALF_EVEN
): BigDecimal = setScale(scale, roundingMode)

fun Double.format(separator: Char = ' '): String {
    val formatter = NumberFormat.getInstance(currentLocale) as DecimalFormat
    val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols

    symbols.groupingSeparator = separator
    formatter.decimalFormatSymbols = symbols
    formatter.maximumFractionDigits = Int.MAX_VALUE

    return formatter.format(this)
}

@Suppress("MagicNumber", "ReturnCount")
fun calculatePercentage(first: Int, second: Int): Double {
    if (first == 0) return 0.0
    if (second == 0) return 0.0

    return (first.toDouble() / second.toDouble()) * 100.0
}

@Suppress("MagicNumber")
fun Int.addZeroPrefixMonth() = if (this < 10) "0$this" else "$this"
