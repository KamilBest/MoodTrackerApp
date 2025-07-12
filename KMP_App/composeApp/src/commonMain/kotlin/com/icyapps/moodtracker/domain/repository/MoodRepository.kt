package com.icyapps.moodtracker.domain.repository

import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo

interface MoodRepository {
    suspend fun getAllMoodTypes(): List<MoodTypeInfo>
    suspend fun submitMood(deviceId: String, mood: Int): MoodEntry
    suspend fun getHistory(deviceId: String): List<MoodEntry>
} 