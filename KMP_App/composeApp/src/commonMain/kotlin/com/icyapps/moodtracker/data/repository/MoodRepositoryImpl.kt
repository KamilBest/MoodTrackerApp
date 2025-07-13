package com.icyapps.moodtracker.data.repository

import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.data.remote.HttpException
import com.icyapps.moodtracker.data.remote.MoodApiService
import com.icyapps.moodtracker.domain.model.AppError
import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import com.icyapps.moodtracker.domain.repository.MoodRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class MoodRepositoryImpl(
    private val apiService: MoodApiService
) : MoodRepository {
    override suspend fun getAllMoodTypes(): ApiResult<List<MoodTypeInfo>> {
        return try {
            val types = apiService.getAllMoodTypes()
            ApiResult.Success(types)
        } catch (e: HttpException) {
            ApiResult.Error(getAppError(e.statusCode, e.responseBody), code = e.statusCode)
        } catch (e: Exception) {
            ApiResult.Error(AppError.FAILED_TO_LOAD_MOOD_TYPES)
        }
    }

    override suspend fun submitMood(deviceId: String, mood: Int): ApiResult<MoodEntry> {
        return try {
            val entry = apiService.submitMood(com.icyapps.moodtracker.data.remote.MoodRequest(deviceId, mood))
            ApiResult.Success(entry)
        } catch (e: HttpException) {
            ApiResult.Error(getAppError(e.statusCode, e.responseBody), code = e.statusCode)
        } catch (e: Exception) {
            ApiResult.Error(AppError.FAILED_TO_SUBMIT_MOOD)
        }
    }

    override suspend fun getHistory(deviceId: String): ApiResult<List<MoodEntry>> {
        return try {
            val history = apiService.getHistory(deviceId)
            ApiResult.Success(history)
        } catch (e: HttpException) {
            ApiResult.Error(getAppError(e.statusCode, e.responseBody), code = e.statusCode)
        } catch (e: Exception) {
            ApiResult.Error(AppError.FAILED_TO_LOAD_HISTORY)
        }
    }
    
    private fun getAppError(statusCode: Int, responseBody: String): AppError {
        return when (statusCode) {
            409 -> {
                // Check if it's a mood already submitted error
                val backendMessage = parseBackendErrorMessage(responseBody)
                if (backendMessage?.contains("Mood already submitted") == true) {
                    AppError.MOOD_ALREADY_SUBMITTED
                } else {
                    AppError.ACCESS_DENIED
                }
            }
            400 -> AppError.INVALID_REQUEST
            401 -> AppError.AUTHENTICATION_FAILED
            403 -> AppError.ACCESS_DENIED
            404 -> AppError.RESOURCE_NOT_FOUND
            500 -> AppError.SERVER_ERROR
            else -> AppError.UNKNOWN_ERROR
        }
    }
    
    private fun parseBackendErrorMessage(errorBody: String): String? {
        return try {
            val json = Json.parseToJsonElement(errorBody).jsonObject
            json["message"]?.jsonPrimitive?.content
        } catch (e: Exception) {
            null
        }
    }
} 