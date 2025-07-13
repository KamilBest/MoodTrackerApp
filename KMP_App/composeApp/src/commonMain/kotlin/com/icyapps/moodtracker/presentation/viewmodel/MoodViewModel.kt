package com.icyapps.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyapps.moodtracker.data.remote.ApiResult
import com.icyapps.moodtracker.domain.model.AppError
import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import com.icyapps.moodtracker.domain.usecase.GetAllMoodTypesUseCase
import com.icyapps.moodtracker.domain.usecase.GetMoodHistoryUseCase
import com.icyapps.moodtracker.domain.usecase.SubmitMoodUseCase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoodViewModel(
    private val getAllMoodTypesUseCase: GetAllMoodTypesUseCase,
    private val submitMoodUseCase: SubmitMoodUseCase,
    private val getMoodHistoryUseCase: GetMoodHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState.asStateFlow()

    private val deviceId = "device_${System.currentTimeMillis()}"

    init {
        loadMoodTypes()
    }

    fun selectMood(mood: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            when (val result = submitMoodUseCase(deviceId, mood)) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        lastSubmittedMood = result.data,
                        successMessage = "Mood submitted successfully!"
                    )
                }
                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = getErrorMessage(result.appError)
                    )
                }
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingHistory = true)
            when (val result = getMoodHistoryUseCase(deviceId)) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingHistory = false,
                        moodHistory = result.data,
                        showHistory = true
                    )
                }
                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingHistory = false,
                        error = getErrorMessage(result.appError)
                    )
                }
            }
        }
    }

    fun hideHistory() {
        _uiState.value = _uiState.value.copy(showHistory = false)
    }

    private fun loadMoodTypes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingTypes = true)
            when (val result = getAllMoodTypesUseCase()) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingTypes = false,
                        moodTypes = result.data
                    )
                }
                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingTypes = false,
                        error = getErrorMessage(result.appError)
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
    
    private fun getErrorMessage(error: AppError): String = when (error) {
        AppError.MOOD_ALREADY_SUBMITTED -> "You've already submitted your mood today"
        AppError.INVALID_REQUEST -> "Invalid request. Please check your input and try again"
        AppError.AUTHENTICATION_FAILED -> "Authentication failed. Please check your settings"
        AppError.ACCESS_DENIED -> "Access denied. You don't have permission for this action"
        AppError.RESOURCE_NOT_FOUND -> "The requested resource was not found"
        AppError.SERVER_ERROR -> "Server error. Please try again later"
        AppError.NETWORK_ERROR -> "Network error. Please check your connection"
        AppError.UNKNOWN_ERROR -> "Something went wrong. Please try again"
        AppError.FAILED_TO_LOAD_MOOD_TYPES -> "Failed to load mood types"
        AppError.FAILED_TO_SUBMIT_MOOD -> "Failed to submit mood"
        AppError.FAILED_TO_LOAD_HISTORY -> "Failed to load mood history"
    }
}

data class MoodUiState(
    val moodTypes: List<MoodTypeInfo> = emptyList(),
    val moodHistory: List<MoodEntry> = emptyList(),
    val lastSubmittedMood: MoodEntry? = null,
    val isLoading: Boolean = false,
    val isLoadingTypes: Boolean = false,
    val isLoadingHistory: Boolean = false,
    val showHistory: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
) 