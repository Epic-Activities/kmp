package com.epicActivities

import androidx.compose.ui.window.ComposeUIViewController
import com.epicActivities.presentation.common.StravaCodeHolder
import com.epicActivities.presentation.common.navigation.NavigationRoot
import com.epicActivities.presentation.common.theme.AppTheme

fun MainViewController() = ComposeUIViewController {
    AppTheme {
        NavigationRoot()
    }
}

// Called from iOSApp.swift via .onOpenURL to handle the Strava OAuth callback
fun handleStravaCallback(url: String) {
    if (url.startsWith("epicactivities://strava-callback")) {
        val code = url.substringAfter("code=").substringBefore("&")
        if (code.isNotEmpty()) StravaCodeHolder.set(code)
    }
}
