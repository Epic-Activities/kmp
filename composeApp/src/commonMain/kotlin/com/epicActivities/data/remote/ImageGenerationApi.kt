package com.epicActivities.data.remote

import com.epicActivities.data.remote.dto.ActivityRequest
import com.epicActivities.data.remote.dto.GenerateImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlin.random.Random
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ImageGenerationApi {
    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 120_000
            socketTimeoutMillis = 120_000
        }
    }

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    suspend fun generate(photoBytes: ByteArray): GenerateImageResponse =
        client.post("$BASE_URL/generate-overlay") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        val activities = HARDCODED_POLYLINES.mapIndexed { i, polyline ->
                            randomActivityRequest(polyline, i)
                        }
                        append("activities", json.encodeToString(activities))
                        append(
                            key = "photo",
                            value = photoBytes,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=\"photo.jpg\"")
                            },
                        )
                    },
                ),
            )
        }.body()

    companion object {
        private const val BASE_URL = "https://generate-photo-api-production.up.railway.app"

        private fun randomActivityRequest(polyline: String, seed: Int): ActivityRequest {
            val rng = Random(seed * 31L + 7L)

            // Distance: 3.0 – 20.0 km, period decimal, no trailing zeros
            val distHundredths = 300 + rng.nextInt(1701)
            val distKm = distHundredths / 100
            val distFrac = distHundredths % 100
            val distRaw = "$distKm.${distFrac.toString().padStart(2, '0')}"
            val distance = "${distRaw.trimEnd('0').trimEnd('.')} km"

            // Pace: 4:00 – 7:00 /km
            val paceTotalSec = 240 + rng.nextInt(181)
            val paceMin = paceTotalSec / 60
            val paceSec = paceTotalSec % 60
            val pace = "$paceMin:${paceSec.toString().padStart(2, '0')} /km"

            // Time = distance × pace, h/m/s units
            val totalSec = (distHundredths * paceTotalSec) / 100
            val hours = totalSec / 3600
            val minutes = (totalSec % 3600) / 60
            val seconds = totalSec % 60
            val time = if (hours > 0) "${hours}h ${minutes}m ${seconds}s" else "${minutes}m ${seconds}s"

            return ActivityRequest(polyline = polyline, distance = distance, pace = pace, time = time)
        }
    }
}
