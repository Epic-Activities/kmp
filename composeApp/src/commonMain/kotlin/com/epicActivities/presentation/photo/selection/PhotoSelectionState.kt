package com.epicActivities.presentation.photo.selection

data class PhotoSelectionState(
    val selectedPhotoUri: String? = null,
    val isLoading: Boolean = false,
)
