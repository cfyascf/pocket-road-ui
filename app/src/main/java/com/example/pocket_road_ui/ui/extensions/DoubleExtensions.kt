package com.example.pocket_road_ui.ui.extensions

import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

fun Double?.toDisplay(suffix: String = ""): String {
    return this?.let { "$it$suffix" } ?: "Unknown"
}

/**
 * Converts a Double to an abbreviated string (K, M, B, T).
 * Examples:
 * 1200.0 -> "1.2K"
 * 289000.0 -> "289K"
 * 2890000.0 -> "2.9M"
 */
fun Double.toAbbreviatedString(): String {
    if (this < 1000) return String.format(Locale.US, "%.0f", this)

    val exp = (ln(this) / ln(1000.0)).toInt()
    val suffixes = listOf("", "K", "M", "B", "T", "P", "E")

    // Fallback for extremely large numbers
    if (exp >= suffixes.size) return String.format(Locale.US, "%.1E", this)

    val value = this / 1000.0.pow(exp.toDouble())

    // Format to 1 decimal place, remove ".0" if it's a whole number (e.g. "100.0K" -> "100K")
    return String.format(Locale.US, "%.1f%s", value, suffixes[exp])
        .replace(".0", "")
}