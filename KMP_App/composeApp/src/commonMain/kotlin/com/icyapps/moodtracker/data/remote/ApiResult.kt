package com.icyapps.moodtracker.data.remote

import com.icyapps.moodtracker.domain.model.AppError

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val appError: AppError,
        val message: String? = null,
        val code: Int? = null
    ) : ApiResult<Nothing>()
} 