package com.icyapps.moodtrackerbackend.constants

object ErrorMessages {
    const val INVALID_MOOD_VALUE = "Invalid mood value. Must be between 1 and 8."
    const val MOOD_ALREADY_SUBMITTED_TODAY = "Mood already submitted today for this device."
    const val DEVICE_ID_REQUIRED = "Device ID is required."
    const val MOOD_VALUE_REQUIRED = "Mood value is required."
    const val INVALID_DEVICE_ID = "Invalid device ID format."
}

object MoodConstants {
    const val MIN_MOOD_VALUE = 1
    const val MAX_MOOD_VALUE = 8
    val VALID_MOOD_RANGE = MIN_MOOD_VALUE..MAX_MOOD_VALUE
} 