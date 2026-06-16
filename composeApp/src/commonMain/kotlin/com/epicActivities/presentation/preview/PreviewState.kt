package com.epicActivities.presentation.preview

data class PreviewState(
    val isGenerating: Boolean = false,
    val generatedImageUrl: String? = null,
    val error: String? = null,
)
