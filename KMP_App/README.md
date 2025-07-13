# Mood Tracker App

A Kotlin Multiplatform mobile app for tracking daily moods, built with Compose Multiplatform and following Clean Architecture principles.

## Quick Start

### Prerequisites
- Your Spring Boot backend running on `http://192.168.1.128:8080`
- Android device connected via USB or Android emulator

### Android
```bash
# Build and install
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:installDebug

# Or run directly
./gradlew :composeApp:runDebug
```

### Desktop
```bash
# Run the app
./gradlew :composeApp:run

# Build distributable packages
./gradlew :composeApp:package

# Build specific platform packages
./gradlew :composeApp:packageMsi    # Windows
./gradlew :composeApp:packageDmg    # macOS
./gradlew :composeApp:packageDeb    # Linux
```

## Configuration

### API Settings
Edit `composeApp/src/commonMain/kotlin/com/icyapps/moodtracker/di/Config.kt`:

```kotlin
object Config {
    const val API_BASE_URL = "http://192.168.1.128:8080"  // Your backend IP
    const val API_KEY = "your-secret-api-key-change-this-in-production"
}
```

**For different setups:**
- **Android Emulator**: `"http://10.0.2.2:8080"`
- **Real Device**: `"http://YOUR_IP:8080"` (e.g., `"http://192.168.1.128:8080"`)
- **Production**: `"https://your-domain.com"`

### Network Security
Update `composeApp/src/androidMain/res/xml/network_security_config.xml` with your backend IP:

```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">192.168.1.128</domain>
    </domain-config>
</network-security-config>
```

## Features

- ✅ **Cross-Platform**: Works on Android and Desktop
- ✅ **Display Mood Types**: Shows all available moods from API
- ✅ **Submit Mood**: Click to submit current mood
- ✅ **View History**: See all submitted moods for the device
- ✅ **Auto Device ID**: Automatically generates unique device identifier
- ✅ **API Key Authentication**: Secure communication with backend
- ✅ **Error Handling**: User-friendly error messages
- ✅ **Loading States**: Visual feedback during API calls
- ✅ **Daily Submission Limit**: Prevents multiple submissions per day

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/moods/types` | Get available mood types |
| `POST` | `/api/moods` | Submit daily mood |
| `GET` | `/api/moods/history?deviceId=string` | Get mood history |

All requests automatically include `X-API-Key` header using a custom Ktor plugin.

## Architecture

```
composeApp/src/commonMain/kotlin/com/icyapps/moodtracker/
├── domain/                    # Business logic layer
│   ├── model/                # Domain entities
│   ├── repository/           # Repository interfaces
│   └── usecase/              # Business use cases
├── data/                     # Data layer
│   ├── remote/               # API service implementation
│   └── repository/           # Repository implementations
├── presentation/             # UI layer
│   ├── screen/               # Compose UI screens
│   └── viewmodel/            # ViewModels
└── di/                       # Dependency injection
    ├── AppModule.kt          # Koin modules
    └── Config.kt             # App configuration
```

## Tech Stack

- **Kotlin Multiplatform**: Shared code between Android and Desktop
- **Compose Multiplatform**: Modern declarative UI framework
- **Ktor**: HTTP client with automatic API key injection
- **Koin**: Dependency injection framework
- **Kotlinx Serialization**: JSON serialization
- **Kotlinx DateTime**: Date/time handling

## Troubleshooting

### Common Issues

1. **"Cleartext communication not permitted"**
   - Update `network_security_config.xml` with correct IP
   - Ensure backend is accessible from device/emulator

2. **"Failed to connect"**
   - Check `Config.kt` has correct IP address
   - Android Emulator: `10.0.2.2:8080`
   - Real Device: Your computer's IP (e.g., `192.168.1.128:8080`)

3. **"Invalid or missing API key"**
   - Check `Config.kt` has correct API key
   - Verify backend is using same API key

## Security Notes

- **API Key**: Change the default API key in production
- **HTTPS**: Use HTTPS for production deployments
- **Network Security**: Configure network security policy for your domains

## Building

```bash
# Build all platforms
./gradlew build

# Build specific platform
./gradlew :composeApp:assembleDebug    # Android
./gradlew :composeApp:compileKotlinDesktop  # Desktop

# Run tests
./gradlew test
```