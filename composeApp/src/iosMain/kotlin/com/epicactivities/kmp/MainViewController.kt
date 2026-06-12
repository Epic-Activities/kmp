package com.epicactivities.kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.epicactivities.kmp.presentation.common.navigation.NavigationRoot
import com.epicactivities.kmp.presentation.common.theme.AppTheme

fun MainViewController() = ComposeUIViewController {
    AppTheme {
        NavigationRoot()
    }
}
