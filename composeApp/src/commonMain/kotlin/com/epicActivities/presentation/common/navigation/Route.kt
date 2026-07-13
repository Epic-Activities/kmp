package com.epicActivities.presentation.common.navigation

import androidx.navigation3.runtime.NavKey
import com.epicActivities.domain.model.Activity
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object StravaActivities : Route

    @Serializable
    data class PhotoSelection(
        val activities: List<Activity>,
    ) : Route

    @Serializable
    data class Preview(
        val activities: List<Activity>,
        val photoUri: String,
    ) : Route
}
