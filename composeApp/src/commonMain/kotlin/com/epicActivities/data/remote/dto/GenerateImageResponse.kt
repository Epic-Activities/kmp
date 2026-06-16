package com.epicActivities.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateImageResponse(
    @SerialName("image_url") val imageUrl: String,
    val prompt: String,
)
