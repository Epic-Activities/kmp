package com.epicActivities.presentation.common.photopicker

import androidx.compose.runtime.Composable

@Composable
expect fun rememberPhotoPicker(onResult: (uri: String?) -> Unit): () -> Unit
