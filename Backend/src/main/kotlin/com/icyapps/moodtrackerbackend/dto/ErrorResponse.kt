package com.icyapps.moodtrackerbackend.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int? = null
) 