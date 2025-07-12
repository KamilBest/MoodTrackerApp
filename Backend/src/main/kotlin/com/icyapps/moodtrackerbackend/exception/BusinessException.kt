package com.icyapps.moodtrackerbackend.exception

import org.springframework.http.HttpStatus

abstract class BusinessException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message) {
    override val message: String = message
}

class MoodAlreadySubmittedException(deviceId: String) : 
    BusinessException("Mood already submitted today for device: $deviceId", HttpStatus.CONFLICT)

class InvalidMoodValueException(value: Int) : 
    BusinessException("Invalid mood value: $value. Must be between 1 and 8.", HttpStatus.BAD_REQUEST)

class DeviceNotFoundException(deviceId: String) : 
    BusinessException("Device not found: $deviceId", HttpStatus.NOT_FOUND) 