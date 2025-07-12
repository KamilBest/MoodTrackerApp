package com.icyapps.moodtracker.di

object Config {
    // Change these values to match your setup
    const val API_BASE_URL = "http://192.168.1.128:8080"
    const val API_KEY = "your-secret-api-key-change-this-in-production"
    
    // For different setups, change the values above:
    // - Android Emulator: "http://10.0.2.2:8080"
    // - Real Device: "http://YOUR_IP:8080" (e.g., "http://192.168.1.128:8080")
    // - Production: "https://your-domain.com"
} 