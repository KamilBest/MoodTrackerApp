# Mood Tracker Backend

A Spring Boot API for tracking daily moods with proper validation and error handling.

## Quick Start

```bash
# Run the application
./gradlew bootRun

# Run tests
./gradlew test
```

The API will be available at `http://localhost:8080`

## Security

This API uses API key authentication. All requests must include the `X-API-Key` header with a valid API key.

**Important:** Change the default API key in `application.properties` before deploying to production.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/moods` | Submit daily mood |
| `GET` | `/api/moods/can-submit?deviceId=string` | Check if mood can be submitted today |
| `GET` | `/api/moods/history?deviceId=string` | Get mood history |
| `GET` | `/api/moods/types` | Get available mood types |

### Mood Values

| Value | Mood | Emoji |
|-------|------|-------|
| 1 | Depressed | 😞 |
| 2 | Sad | 😢 |
| 3 | Tired | 😩 |
| 4 | Neutral | 😐 |
| 5 | Calm | 😌 |
| 6 | Content | 🙂 |
| 7 | Happy | 😊 |
| 8 | Excited | 🤩 |

## Example Usage

```bash
# Check if mood can be submitted today
curl -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  http://localhost:8080/api/moods/can-submit?deviceId=user123

# Submit a mood
curl -X POST http://localhost:8080/api/moods \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  -d '{"deviceId": "user123", "mood": 7}'

# Get mood history
curl -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  http://localhost:8080/api/moods/history?deviceId=user123

# Get mood types
curl -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  http://localhost:8080/api/moods/types
```

## Configuration

Database connection and other settings can be configured in `application.properties`. 

> **Note:** The included `application.properties` contains example configuration for local development. For production, use environment variables or external configuration management.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/moodtracker
spring.datasource.username=mood
spring.datasource.password=mood123
```

### PostgreSQL Setup

See [POSTGRES_SETUP.md](POSTGRES_SETUP.md) for detailed PostgreSQL installation and configuration instructions.

## Tech Stack

- **Spring Boot 3.5.3** with Kotlin
- **PostgreSQL** database
- **Spring Security** for API protection
- **OpenAPI 3.0** for documentation
- **JUnit 5** for testing

## API Testing

You can test the API endpoints using curl or any HTTP client:

```bash
# Test mood submission status
curl -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  http://localhost:8080/api/moods/can-submit?deviceId=user123

# Test mood types endpoint
curl -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  http://localhost:8080/api/moods/types

# Submit a mood
curl -X POST http://localhost:8080/api/moods \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-secret-api-key-change-this-in-production" \
  -d '{"deviceId": "user123", "mood": 7}'
``` 