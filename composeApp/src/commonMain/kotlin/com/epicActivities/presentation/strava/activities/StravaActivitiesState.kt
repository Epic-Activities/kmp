package com.epicActivities.presentation.strava.activities

import com.epicActivities.domain.model.Activity

data class StravaActivitiesState(
    val activities: List<Activity> = emptyList(),
    val selectedIds: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val scrollToTopVersion: Int = 0,
) {
    val selectedActivities: List<Activity>
        get() = activities.filter { it.id in selectedIds }
}
