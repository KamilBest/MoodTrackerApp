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