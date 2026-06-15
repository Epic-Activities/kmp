package com.epicActivities.presentation.photo.selection

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoSelectionViewModel : ViewModel() {
    private val _state = MutableStateFlow(PhotoSelectionState())
    val state: StateFlow<PhotoSelectionState> = _state.asStateFlow()
}
