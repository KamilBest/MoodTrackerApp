package com.icyapps.moodtrackerbackend.service

import com.icyapps.moodtrackerbackend.dto.MoodRequest
import com.icyapps.moodtrackerbackend.dto.MoodResponse
import com.icyapps.moodtrackerbackend.dto.MoodTypeResponse
import com.icyapps.moodtrackerbackend.exception.InvalidDeviceIdException
import com.icyapps.moodtrackerbackend.exception.InvalidMoodValueException
import com.icyapps.moodtrackerbackend.exception.MoodAlreadySubmittedException
import com.icyapps.moodtrackerbackend.model.MoodEntry
import com.icyapps.moodtrackerbackend.model.MoodType
import com.icyapps.moodtrackerbackend.repository.MoodEntryRepository
import com.icyapps.moodtrackerbackend.util.DeviceIdUtils
import com.icyapps.moodtrackerbackend.util.ValidationUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MoodService(private val repository: MoodEntryRepository) {
    
    private val logger = LoggerFactory.getLogger(MoodService::class.java)

    fun submitMood(request: MoodRequest): MoodResponse {
        // Validate device ID
        if (!DeviceIdUtils.isValidDeviceId(request.deviceId)) {
            logger.warn("Invalid device ID format: ${request.deviceId}")
            throw InvalidDeviceIdException(request.deviceId)
        }

        // Hash device ID for privacy
        val hashedDeviceId = DeviceIdUtils.hashDeviceId(request.deviceId)
        logger.info("Submitting mood for device (hashed): ${DeviceIdUtils.getLogSafePrefix(hashedDeviceId)}..., mood value: ${request.mood}")
        
        // Convert to MoodType using validation utility
        val moodType = ValidationUtils.getMoodTypeFromValue(request.mood)
            ?: throw InvalidMoodValueException(request.mood)

        // Check if mood already submitted today using hashed device ID
        if (repository.existsByDeviceIdAndDate(hashedDeviceId, LocalDate.now())) {
            logger.warn(
                "Mood already submitted today for device (hashed): ${
                    DeviceIdUtils.getLogSafePrefix(
                        hashedDeviceId
                    )
                }..."
            )
            throw MoodAlreadySubmittedException(request.deviceId)
        }

        val entry = MoodEntry(
            deviceId = hashedDeviceId,
            mood = moodType,
            date = LocalDate.now(),
            createdAt = java.time.LocalDateTime.now()
        )

        val savedEntry = repository.save(entry)
        logger.info("Successfully saved mood entry: ${savedEntry.id}")
        
        return MoodResponse.fromEntity(savedEntry)
    }

    fun getHistory(deviceId: String): List<MoodResponse> {
        // Hash device ID for privacy
        val hashedDeviceId = DeviceIdUtils.hashDeviceId(deviceId)
        logger.info("Retrieving mood history for device (hashed): ${DeviceIdUtils.getLogSafePrefix(hashedDeviceId)}...")
        return repository.findAllByDeviceIdOrderByDateDesc(hashedDeviceId)
            .map { MoodResponse.fromEntity(it) }
    }

    fun getAllMoodTypes(): List<MoodTypeResponse> {
        return MoodType.entries.map { MoodTypeResponse.fromMoodType(it) }
    }
}