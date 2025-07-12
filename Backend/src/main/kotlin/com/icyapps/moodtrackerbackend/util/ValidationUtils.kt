package com.icyapps.moodtrackerbackend.util

import com.icyapps.moodtrackerbackend.constants.MoodConstants
import com.icyapps.moodtrackerbackend.model.MoodType

object ValidationUtils {
    
    fun isValidMoodValue(value: Int): Boolean {
        return value in MoodConstants.VALID_MOOD_RANGE
    }
    
    fun isValidDeviceId(deviceId: String?): Boolean {
        return !deviceId.isNullOrBlank() && deviceId.trim().isNotEmpty()
    }
    
    fun getMoodTypeFromValue(value: Int): MoodType? {
        return if (isValidMoodValue(value)) {
            MoodType.fromValue(value)
        } else {
            null
        }
    }
} 