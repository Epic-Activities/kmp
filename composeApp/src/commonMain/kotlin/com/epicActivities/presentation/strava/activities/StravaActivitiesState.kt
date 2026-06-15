package com.epicActivities.presentation.strava.activities

import com.epicActivities.domain.model.Activity

data class StravaActivitiesState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
