package com.icyapps.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            try {
                val result = submitMoodUseCase(deviceId, mood)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    lastSubmittedMood = result,
                    successMessage = "Mood submitted successfully!"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to submit mood: ${e.message}"
                )
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingHistory = true)
            try {
                val history = getMoodHistoryUseCase(deviceId)
                _uiState.value = _uiState.value.copy(
                    isLoadingHistory = false,
                    moodHistory = history,
                    showHistory = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingHistory = false,
                    error = "Failed to load history: ${e.message}"
                )
            }
        }
    }

    fun hideHistory() {
        _uiState.value = _uiState.value.copy(showHistory = false)
    }

    private fun loadMoodTypes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingTypes = true)
            try {
                val types = getAllMoodTypesUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoadingTypes = false,
                    moodTypes = types
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingTypes = false,
                    error = "Failed to load mood types: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
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