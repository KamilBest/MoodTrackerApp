package com.icyapps.moodtracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MoodTypeInfo(
    val value: Int,
    val label: String
) 