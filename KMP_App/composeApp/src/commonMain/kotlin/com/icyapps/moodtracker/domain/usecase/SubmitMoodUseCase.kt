package com.icyapps.moodtracker.domain.usecase

import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.repository.MoodRepository

class SubmitMoodUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(deviceId: String, mood: Int): ApiResult<MoodEntry> {
        return repository.submitMood(deviceId, mood)
    }
} 