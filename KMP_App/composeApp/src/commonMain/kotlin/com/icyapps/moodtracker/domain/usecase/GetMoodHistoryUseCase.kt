package com.icyapps.moodtracker.domain.usecase

import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.repository.MoodRepository

class GetMoodHistoryUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(deviceId: String): List<MoodEntry> {
        return repository.getHistory(deviceId)
    }
} 