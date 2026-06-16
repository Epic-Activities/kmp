package com.epicActivities.presentation.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicActivities.domain.usecase.GenerateEpicImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PreviewViewModel : ViewModel() {
    private val generateEpicImageUseCase = GenerateEpicImageUseCase()

    private val _state = MutableStateFlow(PreviewState())
    val state: StateFlow<PreviewState> = _state.asStateFlow()

    fun generateEpic(photoUri: String, polyline: String) {
        viewModelScope.launch {
            _state.update { it.copy(isGenerating = true, error = null) }
            generateEpicImageUseCase(photoUri, polyline)
                .onSuccess { image ->
                    _state.update { it.copy(isGenerating = false, generatedImageUrl = image.imageUrl) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isGenerating = false, error = error.message ?: "Error al generar imagen")
                    }
                }
        }
    }
}
