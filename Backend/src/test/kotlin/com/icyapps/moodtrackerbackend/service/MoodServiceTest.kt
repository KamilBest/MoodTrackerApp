package com.icyapps.moodtrackerbackend.service

import com.icyapps.moodtrackerbackend.dto.MoodRequest
import com.icyapps.moodtrackerbackend.exception.InvalidMoodValueException
import com.icyapps.moodtrackerbackend.exception.MoodAlreadySubmittedException
import com.icyapps.moodtrackerbackend.model.MoodEntry
import com.icyapps.moodtrackerbackend.model.MoodType
import com.icyapps.moodtrackerbackend.repository.MoodEntryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.time.LocalDate
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MoodServiceTest {

    private val repository: MoodEntryRepository = mock()
    private val service = MoodService(repository)

    @Test
    fun `submitMood should create new mood entry successfully`() {
        // Given
        val request = MoodRequest("device123", 5)
        val expectedEntry = MoodEntry(
            id = UUID.randomUUID(),
            deviceId = "device123",
            mood = MoodType.CALM,
            date = LocalDate.now()
        )
        
        whenever(repository.existsByDeviceIdAndDate("device123", LocalDate.now())).thenReturn(false)
        whenever(repository.save(any())).thenReturn(expectedEntry)

        // When
        val result = service.submitMood(request)

        // Then
        assertNotNull(result)
        assertEquals("device123", result.deviceId)
        assertEquals(MoodType.CALM, result.mood)
        verify(repository).save(any())
    }

    @Test
    fun `submitMood should throw exception when mood already submitted today`() {
        // Given
        val request = MoodRequest("device123", 5)
        whenever(repository.existsByDeviceIdAndDate("device123", LocalDate.now())).thenReturn(true)

        // When & Then
        assertThrows<MoodAlreadySubmittedException> {
            service.submitMood(request)
        }
    }

    @Test
    fun `submitMood should throw exception for invalid mood value`() {
        // Given
        val request = MoodRequest("device123", 99)

        // When & Then
        assertThrows<InvalidMoodValueException> {
            service.submitMood(request)
        }
    }
} 