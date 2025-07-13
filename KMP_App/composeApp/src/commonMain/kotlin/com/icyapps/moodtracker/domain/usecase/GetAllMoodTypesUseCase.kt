package com.icyapps.moodtracker.domain.usecase

import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import com.icyapps.moodtracker.domain.repository.MoodRepository

class GetAllMoodTypesUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(): ApiResult<List<MoodTypeInfo>> {
        return repository.getAllMoodTypes()
    }
} 