package com.icyapps.moodtrackerbackend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
class ApplicationProperties {
    
    var mood: MoodProperties = MoodProperties()
    var security: SecurityProperties = SecurityProperties()
    
    data class MoodProperties(
        var maxEntriesPerDay: Int = 1,
        var allowMultipleEntries: Boolean = false
    )
    
    data class SecurityProperties(
        var enableCsrf: Boolean = false,
        var allowedOrigins: List<String> = listOf("*")
    )
} 