package com.example.pocket_road_ui.ui.extensions

fun String?.toDisplay(suffix: String = ""): String {
    return this?.let { "$it$suffix" } ?: "Unknown"
}