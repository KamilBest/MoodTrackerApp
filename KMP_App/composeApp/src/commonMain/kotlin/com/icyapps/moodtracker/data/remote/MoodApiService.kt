package com.icyapps.moodtracker.data.remote

import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import kotlinx.serialization.Serializable

interface MoodApiService {
    suspend fun getAllMoodTypes(): List<MoodTypeInfo>
    suspend fun submitMood(request: MoodRequest): MoodEntry
    suspend fun getHistory(deviceId: String): List<MoodEntry>
    suspend fun canSubmitMoodToday(deviceId: String): MoodSubmissionStatusResponse
}

@Serializable
data class MoodRequest(
    val deviceId: String,
    val mood: Int
)

@Serializable
data class MoodSubmissionStatusResponse(
    val canSubmit: Boolean,
    val message: String
) 