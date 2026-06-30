package com.epicActivities.data.repository

import com.epicActivities.data.remote.ImageGenerationApi
import com.epicActivities.domain.model.GeneratedImage
import com.epicActivities.domain.repository.ImageGenerationRepository
import com.epicActivities.platform.readPhotoBytes
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class ImageGenerationRepositoryImpl(
    private val api: ImageGenerationApi = ImageGenerationApi(),
) : ImageGenerationRepository {
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun generate(photoUri: String): Result<GeneratedImage> =
        runCatching {
            val bytes = readPhotoBytes(photoUri)
            val response = api.generate(bytes)
            val base64 = response.imageUrl.substringAfter("base64,").trim()
            GeneratedImage(imageBytes = Base64.Mime.decode(base64))
        }
}
