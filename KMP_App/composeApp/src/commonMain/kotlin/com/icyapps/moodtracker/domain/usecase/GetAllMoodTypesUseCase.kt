package com.icyapps.moodtracker.domain.usecase

import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import com.icyapps.moodtracker.domain.repository.MoodRepository

class GetAllMoodTypesUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(): List<MoodTypeInfo> {
        return repository.getAllMoodTypes()
    }
} 