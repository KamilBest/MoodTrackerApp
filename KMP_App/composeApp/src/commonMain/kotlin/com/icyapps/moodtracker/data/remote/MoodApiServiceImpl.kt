package com.icyapps.moodtracker.data.remote

import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HttpException(val statusCode: Int, val responseBody: String) : Exception("HTTP $statusCode")

class KermitKtorLogger(private val kermit: KermitLogger) : Logger {
    override fun log(message: String) {
        kermit.d { message }
    }
}

class MoodApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : MoodApiService {

    override suspend fun getAllMoodTypes(): List<MoodTypeInfo> {
        return httpClient.get("$baseUrl/api/moods/types").body()
    }

    override suspend fun submitMood(request: MoodRequest): MoodEntry {
        val response = httpClient.post("$baseUrl/api/moods") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            // Throw an exception with status code and error body for the repository to handle
            throw HttpException(response.status.value, response.bodyAsText())
        }
    }

    override suspend fun getHistory(deviceId: String): List<MoodEntry> {
        return httpClient.get("$baseUrl/api/moods/history") {
            parameter("deviceId", deviceId)
        }.body()
    }

    companion object {
        fun createHttpClient(apiKey: String): HttpClient {
            val kermit = KermitLogger.withTag("KtorHttp")
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(ApiKeyPlugin) {
                    this.apiKey = apiKey
                }
                install(Logging) {
                    logger = KermitKtorLogger(kermit)
                    level = LogLevel.ALL
                }
                // This ensures HTTP error status codes are handled properly
                expectSuccess = false
            }
        }
    }
}