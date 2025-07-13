package com.icyapps.moodtracker.domain.usecase

import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.domain.repository.MoodRepository

class CanSubmitMoodTodayUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(deviceId: String): ApiResult<Boolean> {
        return repository.canSubmitMoodToday(deviceId)
    }
} 