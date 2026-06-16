package com.epicActivities.data.repository

import com.epicActivities.data.remote.ImageGenerationApi
import com.epicActivities.domain.model.GeneratedImage
import com.epicActivities.domain.repository.ImageGenerationRepository
import com.epicActivities.platform.readPhotoBytes

class ImageGenerationRepositoryImpl(
    private val api: ImageGenerationApi = ImageGenerationApi(),
) : ImageGenerationRepository {
    override suspend fun generate(photoUri: String, polyline: String): Result<GeneratedImage> =
        runCatching {
            val bytes = readPhotoBytes(photoUri)
            val response = api.generate(bytes, polyline)
            GeneratedImage(imageUrl = response.imageUrl, prompt = response.prompt)
        }
}
