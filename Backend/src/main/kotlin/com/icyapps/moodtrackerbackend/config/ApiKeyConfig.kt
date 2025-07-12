package com.icyapps.moodtrackerbackend.config

import com.icyapps.moodtrackerbackend.security.ApiKeyAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class ApiKeyConfig {

    @Value("\${app.api.key:default-api-key-change-in-production}")
    private lateinit var apiKey: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(
                ApiKeyAuthenticationFilter(apiKey),
                BasicAuthenticationFilter::class.java
            )
        
        return http.build()
    }
} 