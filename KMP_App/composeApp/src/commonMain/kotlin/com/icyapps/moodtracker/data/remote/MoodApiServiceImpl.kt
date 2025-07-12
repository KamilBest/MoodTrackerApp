package com.icyapps.moodtracker.data.remote

import com.icyapps.moodtracker.domain.model.MoodEntry
import com.icyapps.moodtracker.domain.model.MoodTypeInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MoodApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : MoodApiService {

    override suspend fun getAllMoodTypes(): List<MoodTypeInfo> {
        return httpClient.get("$baseUrl/api/moods/types").body()
    }

    override suspend fun submitMood(request: MoodRequest): MoodEntry {
        return httpClient.post("$baseUrl/api/moods") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun getHistory(deviceId: String): List<MoodEntry> {
        return httpClient.get("$baseUrl/api/moods/history") {
            parameter("deviceId", deviceId)
        }.body()
    }

    companion object {
        fun createHttpClient(apiKey: String): HttpClient {
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
            }
        }
    }
} 