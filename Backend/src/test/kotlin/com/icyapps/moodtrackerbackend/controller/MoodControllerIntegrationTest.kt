package com.icyapps.moodtrackerbackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(MoodController::class)
@ActiveProfiles("test")
class MoodControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var moodService: com.icyapps.moodtrackerbackend.service.MoodService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `getAllMoodTypes should return 200 with mood types`() {
        // Given
        val moodTypes = listOf(
            com.icyapps.moodtrackerbackend.dto.MoodTypeResponse(1, "Depressed"),
            com.icyapps.moodtrackerbackend.dto.MoodTypeResponse(2, "Sad")
        )
        org.mockito.kotlin.whenever(moodService.getAllMoodTypes()).thenReturn(moodTypes)

        // When & Then
        mockMvc.perform(get("/api/moods/types"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].value").value(1))
            .andExpect(jsonPath("$[0].label").value("Depressed"))
    }
} 