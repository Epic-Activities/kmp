package com.epicActivities.presentation.gpx.upload

data class GpxUploadState(
    val selectedFileName: String? = null,
    val isProcessing: Boolean = false,
    val error: String? = null,
)
