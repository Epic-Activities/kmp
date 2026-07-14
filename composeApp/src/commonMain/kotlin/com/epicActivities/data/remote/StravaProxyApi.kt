package com.epicActivities.data.remote

import com.epicActivities.data.remote.dto.StravaActivityDto
import com.epicActivities.data.remote.dto.StravaTokenDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

const val STRAVA_PROXY_BASE_URL = "https://strava-proxy.epicactivities.workers.dev"

class StravaProxyApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun exchangeCode(code: String): StravaTokenDto =
        client.post("$STRAVA_PROXY_BASE_URL/exchange") {
            contentType(ContentType.Application.Json)
            setBody(ExchangeCodeBody(code))
        }.body()

    suspend fun refreshToken(refreshToken: String): StravaTokenDto =
        client.post("$STRAVA_PROXY_BASE_URL/refresh") {
            contentType(ContentType.Application.Json)
            setBody(RefreshTokenBody(refreshToken))
        }.body()

    suspend fun getActivities(accessToken: String, perPage: Int = 100, page: Int = 1): List<StravaActivityDto> =
        client.get("$STRAVA_PROXY_BASE_URL/activities") {
            header("Authorization", "Bearer $accessToken")
            parameter("per_page", perPage)
            parameter("page", page)
        }.body()
}

@Serializable private data class ExchangeCodeBody(val code: String)
@Serializable private data class RefreshTokenBody(val refresh_token: String)
