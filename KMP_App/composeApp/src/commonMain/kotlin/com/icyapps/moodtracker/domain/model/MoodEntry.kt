package com.icyapps.moodtracker.domain.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class MoodEntry(
    val id: String? = null,
    val deviceId: String,
    val mood: MoodType,
    val date: LocalDate,
    val createdAt: String
) 