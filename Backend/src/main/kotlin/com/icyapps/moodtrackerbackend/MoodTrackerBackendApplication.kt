package com.icyapps.moodtrackerbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoodTrackerBackendApplication

fun main(args: Array<String>) {
    runApplication<MoodTrackerBackendApplication>(*args)
}
