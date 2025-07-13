# MoodTracker

A simple mood tracking application that allows users to record their daily moods and view their mood history.

## What I Built

This project consists of:
- **Mobile App**: Kotlin Multiplatform app (Android + Desktop) for submitting and viewing moods
- **Backend API**: Spring Boot service that stores and manages mood data

## Tech Stack

### Frontend (KMP_App/)
- **Kotlin Multiplatform** - Shared code between Android and Desktop
- **Compose Multiplatform** - Modern declarative UI
- **Ktor** - HTTP client for API communication
- **Koin** - Dependency injection

### Backend (Backend/)
- **Spring Boot 3.5.3** with Kotlin
- **PostgreSQL** - Database for storing mood entries
- **Spring Security** - API key authentication
- **JUnit 5** - Testing

## Basic Features

- **Submit Daily Moods** - Users can select and submit their mood once per day
- **Mood History** - View all previously submitted moods
- **Secure API** - Protected with API key authentication
- **Cross-Platform** - Works on Android and Desktop
- **8 Mood Types** - From depressed (😞) to excited (🤩)

## Quick Start

1. **Start Backend**: `cd Backend && ./gradlew bootRun`
2. **Run Mobile App**: `cd KMP_App && ./gradlew :composeApp:runDebug`

See individual README files in `Backend/` and `KMP_App/` for detailed setup instructions. 