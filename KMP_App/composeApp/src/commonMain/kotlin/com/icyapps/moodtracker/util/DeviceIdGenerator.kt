package com.icyapps.moodtracker.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DeviceIdGenerator {
    fun generateDailyDeviceId(): String {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val dayOfYear = today.dayOfYear
        val year = today.year
        
        // Get the device-specific identifier
        val deviceIdentifier = DevicePreferences.getDeviceIdentifier()
        
        // Create a device ID that's unique per device but consistent for the whole day
        // Format: device_YYYY_DDD_XXXXX (year + day of year + device identifier)
        return "device_${year}_${dayOfYear}_${deviceIdentifier}"
    }
} 