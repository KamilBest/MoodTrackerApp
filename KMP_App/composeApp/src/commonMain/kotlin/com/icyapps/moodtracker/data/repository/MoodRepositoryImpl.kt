package com.icyapps.moodtracker.data.repository

import com.icyapps.moodtracker.data.remote.MoodApiService
import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import com.icyapps.moodtracker.domain.repository.MoodRepository

class MoodRepositoryImpl(
    private val apiService: MoodApiService
) : MoodRepository {

    override suspend fun getAllMoodTypes(): List<MoodTypeInfo> {
        return apiService.getAllMoodTypes()
    }

    override suspend fun submitMood(deviceId: String, mood: Int): MoodEntry {
        val request = com.icyapps.moodtracker.data.remote.MoodRequest(deviceId, mood)
        return apiService.submitMood(request)
    }

    override suspend fun getHistory(deviceId: String): List<MoodEntry> {
        return apiService.getHistory(deviceId)
    }
} 