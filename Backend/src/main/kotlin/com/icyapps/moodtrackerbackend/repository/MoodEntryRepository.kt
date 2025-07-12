package com.icyapps.moodtrackerbackend.repository

import com.icyapps.moodtrackerbackend.model.MoodEntry
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface MoodEntryRepository : JpaRepository<MoodEntry, UUID> {
    fun existsByDeviceIdAndDate(deviceId: String, date: LocalDate): Boolean
    fun findAllByDeviceIdOrderByDateDesc(deviceId: String): List<MoodEntry>
}