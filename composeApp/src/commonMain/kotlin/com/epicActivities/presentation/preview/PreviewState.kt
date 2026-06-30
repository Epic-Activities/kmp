package com.epicActivities.presentation.preview

data class PreviewState(
    val isGenerating: Boolean = false,
    val generatedImageBytes: ByteArray? = null,
    val error: String? = null,
)
