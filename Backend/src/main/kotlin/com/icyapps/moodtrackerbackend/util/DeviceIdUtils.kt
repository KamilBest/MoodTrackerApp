package com.icyapps.moodtrackerbackend.util

import java.security.MessageDigest
import java.util.*

object DeviceIdUtils {

    private const val LOG_PREFIX_LENGTH = 8

    /**
     * Hashes a device ID using SHA-256 to protect privacy
     * This ensures that even if someone accesses the database,
     * they cannot identify which specific device submitted the mood
     */
    fun hashDeviceId(deviceId: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(deviceId.toByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }

    /**
     * Validates device ID format
     */
    fun isValidDeviceId(deviceId: String?): Boolean {
        return !deviceId.isNullOrBlank() &&
                deviceId.trim().isNotEmpty() &&
                deviceId.length >= 3 &&
                deviceId.length <= 100
    }

    /**
     * Gets a safe prefix of a hashed device ID for logging purposes
     */
    fun getLogSafePrefix(hashedDeviceId: String): String {
        return hashedDeviceId.take(LOG_PREFIX_LENGTH)
    }
} 