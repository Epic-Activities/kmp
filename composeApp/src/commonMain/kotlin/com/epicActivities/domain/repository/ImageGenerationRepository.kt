package com.epicActivities.domain.repository

import com.epicActivities.domain.model.GeneratedImage

interface ImageGenerationRepository {
    suspend fun generate(photoUri: String, polyline: String): Result<GeneratedImage>
}
