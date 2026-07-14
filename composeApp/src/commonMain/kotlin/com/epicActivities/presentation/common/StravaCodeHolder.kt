package com.epicActivities.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object StravaCodeHolder {
    private val _code = MutableStateFlow<String?>(null)
    val code: StateFlow<String?> = _code.asStateFlow()

    fun set(code: String) {
        _code.value = code
    }

    fun consume() {
        _code.value = null
    }

    private val _disconnected = MutableStateFlow(false)
    val disconnected: StateFlow<Boolean> = _disconnected.asStateFlow()

    fun signalDisconnect() {
        _disconnected.value = true
    }

    fun consumeDisconnect() {
        _disconnected.value = false
    }
}
