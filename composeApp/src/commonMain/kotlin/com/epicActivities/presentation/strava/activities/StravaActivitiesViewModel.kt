package com.epicActivities.presentation.strava.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicActivities.domain.usecase.GetActivitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StravaActivitiesViewModel : ViewModel() {
    private val getActivitiesUseCase = GetActivitiesUseCase()

    private val _state = MutableStateFlow(StravaActivitiesState())
    val state: StateFlow<StravaActivitiesState> = _state.asStateFlow()

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val activities = getActivitiesUseCase()
            _state.update { it.copy(isLoading = false, activities = activities) }
        }
    }
}
