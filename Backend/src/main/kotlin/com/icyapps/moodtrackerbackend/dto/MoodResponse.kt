package com.icyapps.moodtrackerbackend.dto

import com.icyapps.moodtrackerbackend.model.MoodEntry
import com.icyapps.moodtrackerbackend.model.MoodType
import java.time.LocalDate
import java.util.*

data class MoodResponse(
    val id: UUID,
    val deviceId: String,
    val mood: MoodType,
    val date: LocalDate,
    val createdAt: String
) {
    companion object {
        fun fromEntity(entry: MoodEntry): MoodResponse {
            return MoodResponse(
                id = entry.id ?: UUID.randomUUID(),
                deviceId = entry.deviceId,
                mood = entry.mood,
                date = entry.date,
                createdAt = entry.createdAt.toString()
            )
        }
    }
}

data class MoodTypeResponse(
    val value: Int,
    val label: String
) {
    companion object {
        fun fromMoodType(moodType: MoodType): MoodTypeResponse {
            return MoodTypeResponse(
                value = moodType.value,
                label = moodType.label
            )
        }
    }
}

data class MoodSubmissionStatusResponse(
    val canSubmit: Boolean,
    val message: String
) {
    companion object {
        fun canSubmit(): MoodSubmissionStatusResponse {
            return MoodSubmissionStatusResponse(
                canSubmit = true,
                message = "Mood can be submitted today"
            )
        }
        
        fun cannotSubmit(): MoodSubmissionStatusResponse {
            return MoodSubmissionStatusResponse(
                canSubmit = false,
                message = "Mood already submitted today"
            )
        }
        
        fun invalidDeviceId(): MoodSubmissionStatusResponse {
            return MoodSubmissionStatusResponse(
                canSubmit = false,
                message = "Invalid device ID format"
            )
        }
    }
} 