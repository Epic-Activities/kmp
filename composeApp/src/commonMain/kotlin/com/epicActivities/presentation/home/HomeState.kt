package com.epicActivities.presentation.home

data class HomeState(
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
