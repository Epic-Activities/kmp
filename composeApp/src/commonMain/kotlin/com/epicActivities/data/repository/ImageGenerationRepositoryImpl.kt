package com.epicActivities.data.repository

import com.epicActivities.data.remote.ImageGenerationApi
import com.epicActivities.data.remote.dto.ActivityRequest
import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.GeneratedImage
import com.epicActivities.domain.model.SportType
import com.epicActivities.domain.repository.ImageGenerationRepository
import com.epicActivities.platform.readPhotoBytes
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.roundToInt

class ImageGenerationRepositoryImpl(
    private val api: ImageGenerationApi = ImageGenerationApi(),
) : ImageGenerationRepository {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun generate(photoUri: String, activities: List<Activity>): Result<GeneratedImage> =
        runCatching {
            val bytes = readPhotoBytes(photoUri)
            val requests = activities.map { it.toRequest() }
            val response = api.generate(bytes, requests)
            val base64 = response.imageUrl.substringAfter("base64,").trim()
            GeneratedImage(imageBytes = Base64.Mime.decode(base64))
        }

    private fun Activity.toRequest(): ActivityRequest {
        val distance = if (distanceKm >= 10) "%.0f km".format(distanceKm)
                       else "%.1f km".format(distanceKm)

        val h = durationSeconds / 3600
        val m = (durationSeconds % 3600) / 60
        val s = durationSeconds % 60
        val time = if (h > 0) "${h}h ${m}m ${s}s" else "${m}m ${s}s"

        val pace = if (sportType == SportType.Ride) {
            val kmh = distanceKm / (durationSeconds / 3600.0)
            "%.1f km/h".format(kmh)
        } else {
            val secPerKm = (durationSeconds / distanceKm).roundToInt()
            val pm = secPerKm / 60
            val ps = secPerKm % 60
            "$pm:${ps.toString().padStart(2, '0')} /km"
        }

        return ActivityRequest(polyline = polyline, distance = distance, pace = pace, time = time)
    }
}
