package com.epicActivities.presentation.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object StravaActivities : Route

    @Serializable
    data object GpxUpload : Route

    @Serializable
    data class PhotoSelection(val activityId: String) : Route
}
