package com.epicActivities.presentation.common.navigation

import androidx.navigation3.runtime.NavKey

fun <T : NavKey> MutableList<T>.addIfAbsent(route: T) {
    if (route !in this) {
        add(route)
    }
}

fun <T : NavKey> MutableList<T>.popIfNotRoot() {
    if (size > 1) {
        removeLastOrNull()
    }
}
