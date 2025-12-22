package com.example.pocket_road_ui.util

fun Int?.toDisplay(suffix: String = ""): String {
    return this?.let { "$it$suffix" } ?: "Unknown"
}

fun Double?.toDisplay(suffix: String = ""): String {
    return this?.let { "$it$suffix" } ?: "Unknown"
}

fun String?.toDisplay(suffix: String = ""): String {
    return this?.let { "$it$suffix" } ?: "Unknown"
}