package com.icyapps.moodtrackerbackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.icyapps.moodtrackerbackend.dto.MoodRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class MoodControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `getAllMoodTypes should return 200 with mood types`() {
        mockMvc.perform(get("/api/moods/types"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].value").exists())
            .andExpect(jsonPath("$[0].label").exists())
            .andExpect(jsonPath("$[0].emoji").exists())
    }
} 