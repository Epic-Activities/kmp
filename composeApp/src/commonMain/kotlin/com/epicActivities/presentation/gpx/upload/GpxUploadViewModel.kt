package com.epicActivities.presentation.gpx.upload

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GpxUploadViewModel : ViewModel() {
    private val _state = MutableStateFlow(GpxUploadState())
    val state: StateFlow<GpxUploadState> = _state.asStateFlow()

    fun onFileSelected(fileName: String) {
        _state.update { it.copy(selectedFileName = fileName, error = null) }
    }

    fun clearSelectedFile() {
        _state.update { it.copy(selectedFileName = null) }
    }
}
