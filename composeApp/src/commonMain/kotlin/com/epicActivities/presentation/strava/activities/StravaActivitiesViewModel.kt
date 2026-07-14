package com.epicActivities.presentation.strava.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicActivities.domain.usecase.GetStravaActivitiesUseCase
import com.epicActivities.platform.StravaTokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StravaActivitiesViewModel : ViewModel() {
    private val getActivitiesUseCase = GetStravaActivitiesUseCase()
    private val tokenStorage = StravaTokenStorage()

    fun disconnect() {
        tokenStorage.clear()
    }

    private val _state = MutableStateFlow(StravaActivitiesState())
    val state: StateFlow<StravaActivitiesState> = _state.asStateFlow()

    init {
        loadActivities()
    }

    fun toggleSelection(id: String) {
        _state.update { current ->
            val newSelected = if (id in current.selectedIds) {
                current.selectedIds - id
            } else {
                current.selectedIds + id
            }
            current.copy(selectedIds = newSelected)
        }
    }

    private fun loadActivities() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getActivitiesUseCase()
                .onSuccess { activities ->
                    _state.update { it.copy(isLoading = false, activities = activities) }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(isLoading = false, error = e.message ?: "Error al cargar actividades")
                    }
                }
        }
    }
}
