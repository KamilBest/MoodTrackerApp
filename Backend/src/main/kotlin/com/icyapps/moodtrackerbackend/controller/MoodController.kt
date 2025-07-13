package com.icyapps.moodtrackerbackend.controller

import com.icyapps.moodtrackerbackend.dto.MoodRequest
import com.icyapps.moodtrackerbackend.dto.MoodResponse
import com.icyapps.moodtrackerbackend.dto.MoodTypeResponse
import com.icyapps.moodtrackerbackend.dto.MoodSubmissionStatusResponse
import com.icyapps.moodtrackerbackend.service.MoodService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/moods")
class MoodController(private val moodService: MoodService) {

    @PostMapping
    fun submitMood(@Valid @RequestBody request: MoodRequest): ResponseEntity<MoodResponse> {
        val response = moodService.submitMood(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/history")
    fun getHistory(@RequestParam deviceId: String): ResponseEntity<List<MoodResponse>> {
        val history = moodService.getHistory(deviceId)
        return ResponseEntity.ok(history)
    }

    @GetMapping("/types")
    fun getAllMoodTypes(): ResponseEntity<List<MoodTypeResponse>> {
        val types = moodService.getAllMoodTypes()
        return ResponseEntity.ok(types)
    }

    @GetMapping("/can-submit")
    fun canSubmitMoodToday(@RequestParam deviceId: String): ResponseEntity<MoodSubmissionStatusResponse> {
        val status = moodService.canSubmitMoodToday(deviceId)
        return ResponseEntity.ok(status)
    }
}