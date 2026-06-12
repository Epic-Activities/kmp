package com.epicActivities

import androidx.compose.ui.window.ComposeUIViewController
import com.epicActivities.presentation.common.navigation.NavigationRoot
import com.epicActivities.presentation.common.theme.AppTheme

fun MainViewController() = ComposeUIViewController {
    AppTheme {
        NavigationRoot()
    }
}
