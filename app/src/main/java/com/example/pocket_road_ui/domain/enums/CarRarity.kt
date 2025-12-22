package com.example.pocket_road_ui.domain.enums

enum class CarRarity(val label: String, val colorHex: Long) {
    REGULAR("Regular", 0xFF9CA3AF),
    EXOTIC("Exotic", 0xFF9CA3AF),
    RARE("Rare", 0xFF3B82F6),
    SUPERRARE("Super Rare", 0xFFEAB308),
    LEGENDARY("Legendary", 0xFf0000),
    EPIC("Epic", 0xFf0000),
    UNKNOWN("Unknown", 0xFf0000),
}