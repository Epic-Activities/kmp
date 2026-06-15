package com.epicActivities.presentation.strava.activities

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StravaActivitiesViewModel : ViewModel() {
    private val _state = MutableStateFlow(StravaActivitiesState())
    val state: StateFlow<StravaActivitiesState> = _state.asStateFlow()
}
