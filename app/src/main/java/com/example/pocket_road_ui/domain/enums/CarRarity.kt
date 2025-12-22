package com.example.pocket_road_ui.domain.enums

enum class CarRarity(val label: String, val colorHex: Long) {
    REGULAR("Regular", 0xFF9CA3AF),
    RARE("Rare", 0xFF3B82F6),
    EXOTIC("Exotic", 0xFFEAB308),
    LEGENDARY("Legendary", 0xFf0000),
    UNKNOWN("Unknown", 0xFf0000),
}