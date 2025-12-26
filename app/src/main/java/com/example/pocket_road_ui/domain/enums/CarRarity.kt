package com.example.pocket_road_ui.domain.enums

enum class CarRarity(val label: String, val colorHex: Long) {
    COMMON("Common", 0xFF9CA3AF),       // Gray (Zinc 400)
    UNCOMMON("Uncommon", 0xFF10B981),   // Emerald Green
    RARE("Rare", 0xFF3B82F6),           // Royal Blue
    SUPERRARE("Super Rare", 0xFF8B5CF6), // Violet / Purple
    EPIC("Epic", 0xFFD946EF),           // Fuchsia / Neon Pink
    MYTHIC("Mythic", 0xFFEF4444),       // Bright Red (Power/Danger)
    LEGENDARY("Legendary", 0xFFF59E0B), // Gold / Amber
    UNKNOWN("Unknown", 0xFF4B5563)      // Dark Gray (Slate 600)
}

fun String?.toCarRarityEnum(): CarRarity {

    var formattedRarity: String
    if(this == null) {
        formattedRarity = "UNKNOWN"
    } else {
        formattedRarity = this.uppercase()
    }

    val rarityEnum = try {
        CarRarity.valueOf(formattedRarity)
    } catch (e: IllegalArgumentException) {
        println("Error mapping rarity: $this")
        CarRarity.UNKNOWN
    }

    return rarityEnum
}