package com.icyapps.moodtracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class MoodType(val value: Int, val label: String) {
    DEPRESSED(1, "Depressed"),
    SAD(2, "Sad"),
    TIRED(3, "Tired"),
    NEUTRAL(4, "Neutral"),
    CALM(5, "Calm"),
    CONTENT(6, "Content"),
    HAPPY(7, "Happy"),
    EXCITED(8, "Excited");

    companion object {
        /**
         * Converts an integer value to a MoodType.
         * @param value The integer value (1-8)
         * @return The corresponding MoodType or null if invalid
         */
        fun fromValue(value: Int): MoodType? = MoodType.entries.find { it.value == value }
        
        /**
         * Gets the minimum valid mood value
         */
        fun getMinValue(): Int = DEPRESSED.value
        
        /**
         * Gets the maximum valid mood value
         */
        fun getMaxValue(): Int = EXCITED.value
    }
} 