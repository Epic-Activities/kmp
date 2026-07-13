package com.epicActivities.domain.repository

import com.epicActivities.domain.model.Activity
import com.epicActivities.domain.model.GeneratedImage

interface ImageGenerationRepository {
    suspend fun generate(photoUri: String, activities: List<Activity>): Result<GeneratedImage>
}
