package com.epicActivities.data.remote

import com.epicActivities.data.remote.dto.GenerateImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ImageGenerationApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun generate(photoBytes: ByteArray, polyline: String): GenerateImageResponse =
        client.post("$BASE_URL/generate") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("polyline", polyline)
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
    }
}
