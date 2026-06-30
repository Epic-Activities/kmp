package com.epicActivities.domain.usecase

import com.epicActivities.data.repository.ImageGenerationRepositoryImpl
import com.epicActivities.domain.model.GeneratedImage
import com.epicActivities.domain.repository.ImageGenerationRepository

class GenerateEpicImageUseCase(
    private val repository: ImageGenerationRepository = ImageGenerationRepositoryImpl(),
) {
    suspend operator fun invoke(photoUri: String): Result<GeneratedImage> =
        repository.generate(photoUri)
}
