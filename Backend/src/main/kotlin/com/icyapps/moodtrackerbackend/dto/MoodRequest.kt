package com.icyapps.moodtrackerbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max

data class MoodRequest(
    @field:NotBlank(message = "Device ID is required")
    val deviceId: String,
    
    @field:NotNull(message = "Mood value is required")
    @field:Min(value = 1, message = "Mood value must be at least 1")
    @field:Max(value = 8, message = "Mood value must be at most 8")
    val mood: Int,
)