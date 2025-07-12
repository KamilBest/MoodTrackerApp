package com.icyapps.moodtracker.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.util.*

class ApiKeyPlugin(private val apiKey: String) {
    class Config {
        var apiKey: String = ""
    }

    companion object Plugin : HttpClientPlugin<Config, ApiKeyPlugin> {
        override val key = AttributeKey<ApiKeyPlugin>("ApiKeyPlugin")

        override fun prepare(block: Config.() -> Unit): ApiKeyPlugin {
            val config = Config().apply(block)
            return ApiKeyPlugin(config.apiKey)
        }

        override fun install(plugin: ApiKeyPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                context.headers.append("X-API-Key", plugin.apiKey)
            }
        }
    }
} 