package com.icyapps.moodtrackerbackend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class ApiKeyAuthenticationFilter(private val apiKey: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val providedApiKey = request.getHeader("X-API-Key")

        if (providedApiKey == null || providedApiKey != apiKey) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid or missing API key")
            return
        }

        // Create a simple authentication token
        val authentication = UsernamePasswordAuthenticationToken(
            "api-client",
            null,
            emptyList()
        )

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
} 