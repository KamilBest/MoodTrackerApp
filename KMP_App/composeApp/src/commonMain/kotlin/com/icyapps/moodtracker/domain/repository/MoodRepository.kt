package com.icyapps.moodtracker.domain.repository

import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo

interface MoodRepository {
    suspend fun getAllMoodTypes(): ApiResult<List<MoodTypeInfo>>
    suspend fun submitMood(deviceId: String, mood: Int): ApiResult<MoodEntry>
    suspend fun getHistory(deviceId: String): ApiResult<List<MoodEntry>>
    suspend fun canSubmitMoodToday(deviceId: String): ApiResult<Boolean>
} 