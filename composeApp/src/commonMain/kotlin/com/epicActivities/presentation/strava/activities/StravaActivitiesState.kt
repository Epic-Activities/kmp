package com.epicActivities.presentation.strava.activities

data class StravaActivitiesState(
    val isLoading: Boolean = false,
    val error: String? = null,
)
