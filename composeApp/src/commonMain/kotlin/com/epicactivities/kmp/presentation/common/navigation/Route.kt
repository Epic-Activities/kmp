package com.epicactivities.kmp.presentation.common.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Detail : Route
}
